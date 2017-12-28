package com.pubhub.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.pubhub.model.Card;
import com.pubhub.model.Menu;
import com.pubhub.model.Order;
import com.pubhub.model.Product;
import com.pubhub.model.Pub;
import com.pubhub.model.User;

@Controller
public class PubhubController {
	
	ArrayList<Product> cart = new ArrayList<Product>();
	
	ArrayList<Menu> menus = new ArrayList<Menu>();

	ArrayList<Pub> pubs = new ArrayList<Pub>();
	
	ArrayList<Pub> activePubs = new ArrayList<Pub>();
	
	ArrayList<User> users = new ArrayList<User>();
	
	ArrayList<Order> orders = new ArrayList<Order>();
	
	ArrayList<User> active = new ArrayList<User>();
	
	ArrayList<Card> cards = new ArrayList<Card>();
    private static String UPLOADED_FOLDER = "/tmp/";
	
	User currentUser;
	Pub currentPub;
	
	int thisPubId = 0;
	int thisUserId = 0;
	
	
	
	boolean loggedIn;
	
	@RequestMapping("/")
    public String greet(@ModelAttribute User user, Model model) {
        model.addAttribute(user);
        return "log";
    }
	
	@RequestMapping("/cart")
    public String cart(@CookieValue(value = "cid", required = false) String id, Model model, SessionStatus status, HttpSession ses) {
		ses.removeAttribute("cart1");
		ses.removeAttribute("cart2");
		
		status.setComplete();
		
		ArrayList<Product> res1 = new ArrayList<Product>();
		ArrayList<Product> res2 = new ArrayList<Product>();
		
		ArrayList<Product> car = new ArrayList<Product>();
		
		System.out.println("ID " + id);
		
		for(User u : users) {
			if(u.getId() == Integer.parseInt(id)) {
				car.addAll(u.getCart());
			}
		}
		
		model.addAttribute("totalCart", totalCart(car));
		
		int numCart = car.size();
		
		for(int i = 0; i < (numCart/2); i++) {
			res1.add(car.get(i));
		}
		
		for(int i = (numCart/2); i < numCart; i++) {
			res2.add(car.get(i));
		}
		
		
		
		model.addAttribute("cart1", res1);
		model.addAttribute("cart2", res2);
        return "cart";
    }

	
	@RequestMapping("/account")
    public String acc(@CookieValue(value = "pid", required = false) String pid, @CookieValue(value = "acc", required = false) String acc,@CookieValue(value = "pub", required = false) String pub, @ModelAttribute Product prod, BindingResult br, Model model, HttpSession ses) {
 
        model.addAttribute("product", prod);
        model.addAttribute("pubp", new Pub());
        
        ses.removeAttribute("men1");
		ses.removeAttribute("men2");

        
        ArrayList<Product> men1 = new ArrayList<Product>();
        ArrayList<Product> men2 = new ArrayList<Product>();
        ArrayList<Product> tmp = new ArrayList<Product>();
        
        try {
        	for(Pub p : pubs) {
            	if(p.getId() == Integer.parseInt(pid)) {
            		tmp = p.getMenu();
            	}
            }
        } catch (Exception e) {
        	
        }
        
        try {
        	int maxPubs = tmp.size();
    		for(int i = 0; i < (maxPubs/2); i++) {
    			men1.add(tmp.get(i));
    		}
    		for(int i = (maxPubs/2); i < maxPubs; i++) {
    			men2.add(tmp.get(i));
    		}
    		
    		model.addAttribute("men1", men1);
    		model.addAttribute("men2", men2);
            
        } catch (Exception e) {
        	
        }
        
		
        
		try {
			if(Integer.parseInt(pub) == 22)
				return "account";

		} catch(Exception e) {

		}

		try {
			if(Integer.parseInt(acc) >= 0)
				return "acc";

		} catch (Exception e) {

		}

       return "no user";
    } 
	
	@RequestMapping("/home")
    public String home(@ModelAttribute Product prod, BindingResult br, Model model, SessionStatus status, HttpSession ses) {
		ses.removeAttribute("pubs1");
		ses.removeAttribute("pubs2");
		ses.removeAttribute("pubs3");
		ses.removeAttribute("pubs4");
		
		status.setComplete();
		
		ArrayList<Pub> res1 = new ArrayList<Pub>();
		ArrayList<Pub> res2 = new ArrayList<Pub>();
		ArrayList<Pub> res3 = new ArrayList<Pub>();
		ArrayList<Pub> res4 = new ArrayList<Pub>();
		
		int maxPubs = pubs.size();
		for(int i = 0; i < (maxPubs/4); i++) {
			res1.add(pubs.get(i));
		}
		for(int i = (maxPubs/4); i < (maxPubs/2); i++) {
			res2.add(pubs.get(i));
		}
		for(int i = (maxPubs/2); i < ((maxPubs/4) + (maxPubs/2)); i++) {
			res3.add(pubs.get(i));
		}
		for(int i = ((maxPubs/4) + (maxPubs/2)); i < maxPubs; i++) {
			res4.add(pubs.get(i));
		}
		model.addAttribute("pubs1", res1);
		model.addAttribute("pubs2", res2);
		model.addAttribute("pubs3", res3);
		model.addAttribute("pubs4", res4);
        return "index";
    } 
	
	@RequestMapping(value = "/addCart", method = { RequestMethod.GET, RequestMethod.POST })
    public String addCart(@CookieValue(value = "id", required = false) String uid,@CookieValue(value = "pub", required = false) String pub, @RequestParam("hiddenid") int id, @RequestParam("pubid") int pubid, Model model, SessionStatus status, HttpSession ses) throws CloneNotSupportedException {
        System.out.println("Prod " + id);
        System.out.println("Pub " + pubid);
        
        ArrayList<Product> wait = null;
        ArrayList<Product> car = new ArrayList<Product>();
        try {
        	
        	Pub non = null;
        	for(Pub p : pubs) {
        		if(p.getId() == pubid)
        			non = p;
        	}
        	
        	for(User u : users) {
        		if(u.getId() == Integer.parseInt(uid)) {
        			Product s = non.getMenuItem(id);
        			s.setCartId(u.genCartId());
        			u.addCart(s);
        			System.out.println("THIS CART" + u.getCart().size());
        		}
        		
        	}
  	
        } catch (Exception e) {
        	
        }
        
        for(Product a : car) {
        	System.out.println(a.toString());
        }
        
        model.addAttribute("thisPub", pubid);
        ArrayList<Product> current = null;
        int maxPubs = 0;
        
        try {
        	for(Pub u : pubs) {
            	if(u.getId() == pubid) {
            		current = u.getMenu();
            	}
        	}	
        	maxPubs = current.size();
        } catch(Exception e) {
        	
        }
        
        model.addAttribute("thisPub", pubid);
        
        ses.removeAttribute("menu1");
		ses.removeAttribute("menu2");
		ses.removeAttribute("menu3");
		ses.removeAttribute("menu4");
		
		status.setComplete();


		ArrayList<Product> res1 = new ArrayList<Product>();
		ArrayList<Product> res2 = new ArrayList<Product>();
		ArrayList<Product> res3 = new ArrayList<Product>();
		ArrayList<Product> res4 = new ArrayList<Product>();
		
		try {
			for(int i = 0; i < (maxPubs/4); i++) {
				res1.add(current.get(i));
			}
		} catch (Exception e) {

		}
		try {
			for(int i = (maxPubs/4); i < (maxPubs/2); i++) {
				res2.add(current.get(i));
			}
		} catch (Exception e) {
		
		}

		try {
			for(int i = (maxPubs/2); i < ((maxPubs/4) + (maxPubs/2)); i++) {
				res3.add(current.get(i));
			}
		} catch (Exception e) {
		
		}
		try {
			for(int i = ((maxPubs/4) + (maxPubs/2)); i < maxPubs; i++) {
				res4.add(current.get(i));
			}
		} catch (Exception e) {
		
		}

		model.addAttribute("menu1", res1);
		model.addAttribute("menu2", res2);
		model.addAttribute("menu3", res3);
		model.addAttribute("menu4", res4);
        
        
        
        return "menu";
    }
	
	@RequestMapping(value = "/chgPro", method = { RequestMethod.GET, RequestMethod.POST })
	public String chgPro(@ModelAttribute Pub pub, @ModelAttribute Product prod, @CookieValue(value = "ppid", required = false) String pid, Model model) {
		
		model.addAttribute("pubp", pub);
		model.addAttribute("product", prod);
		
		String email = pub.getEmail();
		String password = pub.getPassword();
		String bio = pub.getBio();
		String desc = pub.getDesc();
		
		if(email != null) {
			for(Pub p : pubs) {
				if(p.getId() == Integer.parseInt(pid)) {
					p.setEmail(email);
					System.out.println(p.toString());
				}	
			}
		}
		
		if(password != null) {
			for(Pub p : pubs) {
				if(p.getId() == Integer.parseInt(pid)) {
					p.setPassword(password);
					System.out.println(p.toString());
				}
			}		
		}
		
		if(bio != null) {
			for(Pub p : pubs) {
				if(p.getId() == Integer.parseInt(pid)) {
					p.setBio(bio);
					System.out.println(p.toString());
				}				
			}
		}
		
		if(desc != null) {
			for(Pub p : pubs) {
				if(p.getId() == Integer.parseInt(pid)) {
					p.setDesc(desc);
					System.out.println(p.toString());
				}
			}
		}
			
		return "account";
	}
	
	public float totalCart(ArrayList<Product> res) {
		float total = 0;
		
		for(Product s : res) {
			total += s.getPrice();
		}
		return total;
	}

	@RequestMapping(value = "/rmCart", method = { RequestMethod.GET, RequestMethod.POST })
    public String rmCart(@RequestParam("hiddenid") String id, @RequestParam("pubid") String pubid, @CookieValue(value = "usid", required = false) String uid,Model model, SessionStatus status, HttpSession ses) {
		model.addAttribute("thisPub", pubid);
		
		ses.removeAttribute("cart1");
		ses.removeAttribute("cart2");
		
		status.setComplete();
		
		ArrayList<Product> car = new ArrayList<Product>();
		for(User u : users) {
			if(u.getId() == Integer.parseInt(uid)) {
				car = u.getCart();
			}
		}
		
		try {
			for(Product s : car) {
				if(s.getCartId() == Integer.parseInt(pubid)) {
					car.remove(s);
				}
			}
		} catch (Exception e) {
			
		}
		
		model.addAttribute("totalCart", totalCart(car));
		
		
		ArrayList<Product> res1 = new ArrayList<Product>();
		ArrayList<Product> res2 = new ArrayList<Product>();
		
		int numCart = car.size();
		
		for(int i = 0; i < (numCart/2); i++) {
			res1.add(car.get(i));
		}
		
		for(int i = (numCart/2); i < numCart; i++) {
			res2.add(car.get(i));
		}
		
		
		
		model.addAttribute("cart1", res1);
		model.addAttribute("cart2", res2);
		
		System.out.println(id);
		System.out.println(pubid);
		
	
		
		
        return "cart";
    }
	@RequestMapping("/pay")
    public String pay(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	
	@RequestMapping("/login")
    public String login(@CookieValue(value = "id", required = false) String id,@CookieValue(value = "pid", required = false) String pid, @ModelAttribute User user, BindingResult br, Model model, SessionStatus status, HttpSession ses, HttpServletResponse response) {
		User u = new User();
		u.setName(user.getName());
		model.addAttribute("user", u);
		model.addAttribute("user", user);
		
		addPubs();
		ses.removeAttribute("pubs1");
		ses.removeAttribute("pubs2");
		ses.removeAttribute("pubs3");
		ses.removeAttribute("pubs4");
		
		status.setComplete();
		ArrayList<Pub> res1 = new ArrayList<Pub>();
		ArrayList<Pub> res2 = new ArrayList<Pub>();
		ArrayList<Pub> res3 = new ArrayList<Pub>();
		ArrayList<Pub> res4 = new ArrayList<Pub>();
		
		
		int maxPubs = pubs.size();
		try {

			for(int i = 0; i < (maxPubs/4); i++) {
				res1.add(pubs.get(i));
			}
		} catch(Exception e) {

		}

		try {
			for(int i = (maxPubs/4); i < (maxPubs/2); i++) {
				res2.add(pubs.get(i));
			}
		} catch(Exception e) {

		}

		try {
			for(int i = (maxPubs/2); i < ((maxPubs/4) + (maxPubs/2)); i++) {
				res3.add(pubs.get(i));
			}
		} catch(Exception e) {

		}

		try {
			for(int i = ((maxPubs/4) + (maxPubs/2)); i < maxPubs; i++) {
				res4.add(pubs.get(i));
			}
		} catch(Exception e) {

		}

		
		model.addAttribute("pubs1", res1);
		model.addAttribute("pubs2", res2);
		model.addAttribute("pubs3", res3);
		model.addAttribute("pubs4", res4);
		
		System.out.println(user.getEmail());
		System.out.println(user.getPassword());
		System.out.println(users.size());
		
		model.addAttribute("pub", new Pub());
		
		String email = user.getName();
		System.out.println(email);
		String pass = user.getPassword();
		System.out.println(pass);
	
		
		try {
			for(User a : users) {
				if(a.getEmail().equalsIgnoreCase(email) && a.getPassword().equals(pass)) {
					active.add((User)a.clone());
					Cookie c = new Cookie("cid", Integer.toString(a.getId()));
					c.setPath("/cart");
					response.addCookie(c);
					Cookie c3 = new Cookie("usid", Integer.toString(a.getId()));
					c3.setPath("/rmCart");
					response.addCookie(c3);
					Cookie d = new Cookie("id", Integer.toString(a.getId()));
					d.setPath("/addCart");
					response.addCookie(d);
					Cookie g = new Cookie("acc", Integer.toString(a.getId()));
					g.setPath("/account");
					response.addCookie(g);
					Cookie c1 = new Cookie("pid", null);
					c1.setPath("/cart");
					c1.setMaxAge(0);
					response.addCookie(c);
					Cookie d1 = new Cookie("pid", null);
					d.setPath("/addCart");
					d1.setMaxAge(0);
					response.addCookie(d1);
					Cookie e1 = new Cookie("pid", null);
					e1.setPath("/account");
					e1.setMaxAge(0);
					response.addCookie(e1);
					Cookie q1 = new Cookie("pub", null);
					q1.setPath("/account");
					q1.setMaxAge(0);
					response.addCookie(q1);
					Cookie f1 = new Cookie("pid", null);
					f1.setPath("/addPub");
					f1.setMaxAge(0);
					response.addCookie(f1);
					Cookie g1 = new Cookie("pid", null);
					g1.setPath("/addMenu");
					g1.setMaxAge(0);
					response.addCookie(g1);
					return "index";
				}
			}
		} catch (Exception e) {
			
		}
		
		try {
			for(Pub b : pubs) {
				if(b.getEmail().equalsIgnoreCase(email) && b.getPassword().equals(pass)) {
						activePubs.add((Pub) b.clone());
						
						Cookie e2 = new Cookie("paid", null);
						e2.setPath("/account");
						e2.setMaxAge(0);
						response.addCookie(e2);
						Cookie q2 = new Cookie("pub", null);
						q2.setPath("/account");
						q2.setMaxAge(0);
						response.addCookie(q2);
						Cookie g2 = new Cookie("pbid", null);
						g2.setPath("/addMenu");
						g2.setMaxAge(0);
						response.addCookie(g2);
						
						Cookie c = new Cookie("pid", Integer.toString(b.getId()));
						c.setPath("/cart");
						response.addCookie(c);
						
						Cookie d = new Cookie("pcid", Integer.toString(b.getId()));
						d.setPath("/addCart");
						response.addCookie(d);
						
						Cookie e = new Cookie("paid", Integer.toString(b.getId()));
						e.setPath("/account");
						e.setMaxAge(60*24);
						response.addCookie(e);
						
				
						
						Cookie g12 = new Cookie("ppid", Integer.toString(b.getId()));
						g12.setPath("/chgPro");
						g12.setMaxAge(60*24);
						response.addCookie(g12);
						
						Cookie q = new Cookie("pub", "22");
						q.setPath("/account");
						q.setMaxAge(60*24);
						response.addCookie(q);
						Cookie f = new Cookie("puid", Integer.toString(b.getId()));
						f.setPath("/addPub");
						f.setMaxAge(60*24);
						response.addCookie(f);
						
						Cookie g = new Cookie("pbid", Integer.toString(b.getId()));
						g.setPath("/addMenu");
						g.setMaxAge(60*24);
						response.addCookie(g);
						
						Cookie c1 = new Cookie("id", null);
						c1.setPath("/cart");
						c1.setMaxAge(0);
						response.addCookie(c1);
						
						Cookie d1 = new Cookie("id", null);
						d1.setPath("/addCart");
						d1.setMaxAge(0);
						response.addCookie(d1);
						Cookie g1 = new Cookie("acc", null);
						g1.setPath("/account");
						g1.setMaxAge(0);
						response.addCookie(g1);
						
							
						return "pubaccount";
						
				}
			}
		} catch(Exception e) {
			
		}
		
		System.out.println(id);

		return "log";
    }
	
	public static double distanceBetweenCoords(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
	
	public File convert(MultipartFile file)
	{    
	    File convFile = new File(file.getOriginalFilename());
	    try {
			convFile.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	    FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(convFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    try {
			fos.write(file.getBytes());
			 fos.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    return convFile;
	}
	
	@PostMapping("/register")
    public String reg(@ModelAttribute User user,@RequestParam(value = "owner", required = false) boolean retCardChecked, BindingResult br, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("user", new User());
		System.out.println(user.getName());
		System.out.println(user.getEmail());
		System.out.println(user.getPassword());
		
		Pub p = new Pub();
		User u = new User();
		if(retCardChecked) {
			p.setId(getMaxPubId() + 1);
			p.setEmail(user.getEmail());
			p.setName(user.getName());
			p.setPassword(user.getPassword());
			
			pubs.add(p);
		} else {
			u.setId(getMaxUserId() + 1);
			u.setName(user.getName());
			u.setPassword(user.getPassword());
			u.setEmail(user.getEmail());
			
			users.add(u);
		}
		System.out.println(retCardChecked);
		System.out.println(p.toString());
		System.out.println(u.toString());
        return "log";
    }
	
	public void syncPubs() {

	}
	
	public int getMaxUserId() {
		
		int max = 0;
		for(User u : users) {
			if(u.getId() > max)
				max = u.getId();
		}
		
		return max;
	}
	
	public int getMaxPubId() {
		
		int max = 0;
		for(Pub u : pubs) {
			if(u.getId() > max)
				max = u.getId();
		}
		
		return max;
	}
	
	
	@RequestMapping(value = "/menu", method = { RequestMethod.GET, RequestMethod.POST })
    public String menu(@ModelAttribute("p") Pub pub, @RequestParam("hiddenid") int id,Model model, SessionStatus status, HttpSession ses) {
        model.addAttribute("p", pub);
        
        System.out.println(id);
        ArrayList<Product> current = null;
        int maxPubs = 0;
        
        try {
        	for(Pub u : pubs) {
            	if(u.getId() == id) {
            		current = u.getMenu();
            	}
        	}	
        	maxPubs = current.size();
        } catch(Exception e) {
        	
        }
        
        
        
        model.addAttribute("thisPub", id);
        
        ses.removeAttribute("menu1");
		ses.removeAttribute("menu2");
		ses.removeAttribute("menu3");
		ses.removeAttribute("menu4");
		
		status.setComplete();


		ArrayList<Product> res1 = new ArrayList<Product>();
		ArrayList<Product> res2 = new ArrayList<Product>();
		ArrayList<Product> res3 = new ArrayList<Product>();
		ArrayList<Product> res4 = new ArrayList<Product>();
		
		
		
		try {
			for(int i = 0; i < (maxPubs/4); i++) {
				res1.add(current.get(i));
			}
		} catch (Exception e) {

		}
		try {
			for(int i = (maxPubs/4); i < (maxPubs/2); i++) {
				res2.add(current.get(i));
			}
		} catch (Exception e) {
		
		}

		try {
			for(int i = (maxPubs/2); i < ((maxPubs/4) + (maxPubs/2)); i++) {
				res3.add(current.get(i));
			}
		} catch (Exception e) {
		
		}
		try {
			for(int i = ((maxPubs/4) + (maxPubs/2)); i < maxPubs; i++) {
				res4.add(current.get(i));
			}
		} catch (Exception e) {
		
		}
		
		for(Product q : res1) {
			System.out.println(q.toString());
		}
		
		for(Product q : res2) {
			System.out.println(q.toString());
		}
		
		for(Product q : res3) {
			System.out.println(q.toString());
		}
		
		for(Product q : res4) {
			System.out.println(q.toString());
		}

		model.addAttribute("menu1", res1);
		model.addAttribute("menu2", res2);
		model.addAttribute("menu3", res3);
		model.addAttribute("menu4", res4);
  
        return "menu";
    }
	
	@RequestMapping("/removemenu")
    public String rmMenu(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	
	@RequestMapping("/search")
    public String search(Model model) {
        //Search by pub name
		
		//Location
		
		//description
		
		//products on menu - their descriptions
		
		
        return "index";
    }
	
	@RequestMapping("/changeProfiler")
    public String img(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	
	@RequestMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	
	
	@RequestMapping("/changeDesc")
    public String desc(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	
	@RequestMapping("/changeBio")
    public String bio(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	
	@RequestMapping("/email")
    public String email(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	
	@RequestMapping("/password")
    public String password(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	
	@RequestMapping("/location")
    public String loc(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	@RequestMapping("/image")
    public String image(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	
	@RequestMapping(value = "/addMenu", method = { RequestMethod.GET, RequestMethod.POST })
	public String addMenu(@CookieValue(value = "pbid", required = false) String pid, @ModelAttribute Product prod, BindingResult br, Model model, HttpSession ses) {
		String path = "C:\\Users\\Adam\\eclipse-workspace\\pubhub\\src\\main\\resources\\static\\";
		
		Product generated = new Product();

		ArrayList<Product> men = new ArrayList<Product>();
		
		
		
		try {
			model.addAttribute("product", prod);
			model.addAttribute("pubp", new Pub());
			
			generated.setName(prod.getName());
			generated.setDesc(prod.getDesc());
			generated.setPrice(prod.getPrice());
			generated.setImage(prod.getImageData().getOriginalFilename());
			
			System.out.println(prod.getName());
			System.out.println(prod.getDesc());
			System.out.println(prod.getPrice());
			System.out.println(prod.getImageData().getSize());
			System.out.println(prod.getImageData().getOriginalFilename());

			for(Pub p : pubs) {
				if(p.getId() == Integer.parseInt(pid))
					generated.setId(p.genNewMenuId());
					p.addMenu(generated);
			}

		} catch (Exception e) {
			System.out.println("CANT ADD");
		}

		ses.removeAttribute("men1");
		
		ses.removeAttribute("men2");
		
		try {
			ArrayList<Product> men1 = new ArrayList<Product>();
			ArrayList<Product> men2 = new ArrayList<Product>();
			ArrayList<Product> tmp = new ArrayList<Product>();
			
			for(Pub p : pubs) {
				if(p.getId() == Integer.parseInt(pid)) {
					tmp = p.getMenu();
				}
			}

			int maxPubs = tmp.size();
			for(int i = 0; i < (maxPubs/2); i++) {
				men1.add(tmp.get(i));
			}
			for(int i = (maxPubs/2); i < maxPubs; i++) {
				men2.add(tmp.get(i));
			}

			model.addAttribute("men1", men1);
			model.addAttribute("men2", men2);
		} catch (Exception e) {
			System.out.println("DISPLAY ERROR");
		}

		try {
			File f = convert(prod.getImageData());
			FileUtils utils = new FileUtils();
			FileOutputStream out = new FileOutputStream(path + prod.getImageData().getOriginalFilename());
			byte[] b = utils.readFileToByteArray(f);

			out.write(b);
			out.close();
		} catch (Exception e) {
			System.out.println("CANT WRITE");
		}



		return "account";
    }
	
	@RequestMapping(value = "/addPub", method = { RequestMethod.GET, RequestMethod.POST })
	public String addPub(@CookieValue(value = "puid", required = false) String pid,@ModelAttribute("pub") Pub pub, BindingResult br, Model model, SessionStatus status, HttpSession ses) {
		
		
		String path = "C:\\Users\\Adam\\eclipse-workspace\\pubhub\\src\\main\\resources\\static\\";
		Pub generated = new Pub();
		
		System.out.println("Generated");
		
		boolean added = false;
		
		System.out.println("Generated");
		
		System.out.println("PID" + Integer.parseInt(pid));
		for(Pub s : pubs) {
			System.out.println(s.toString());
			System.out.println(s.getId());
		}
		
		
		
		
		try {
			
			for(Pub p : pubs) {
				if(p.getId() == Integer.parseInt(pid)) {
					added = true;
					p.setLocation(pub.getLocation());
					p.setBio(pub.getBio());
					p.setDesc(pub.getDesc());
					p.setImage(pub.getImageData().getOriginalFilename());
					p.setImageData(pub.getImageData());
				}
			}
		} catch (Exception e) {	
		}
		
		try {
			FileOutputStream out = new FileOutputStream(path + pub.getImageData().getOriginalFilename());
			
			out.write(pub.getImageData().getBytes());
			out.close();
		} catch(Exception e) {
			System.out.println("Failed writing file");
		}

		
		ses.removeAttribute("pubs1");
		ses.removeAttribute("pubs2");
		ses.removeAttribute("pubs3");
		ses.removeAttribute("pubs4");
		status.setComplete();
		
		model.addAttribute("p", pub);
		
		ArrayList<Pub> res1 = new ArrayList<Pub>();
		ArrayList<Pub> res2 = new ArrayList<Pub>();
		ArrayList<Pub> res3 = new ArrayList<Pub>();
		ArrayList<Pub> res4 = new ArrayList<Pub>();
		int maxPubs = pubs.size();
		for(int i = 0; i < (maxPubs/4); i++) {
			res1.add(pubs.get(i));
		}
		for(int i = (maxPubs/4); i < (maxPubs/2); i++) {
			res2.add(pubs.get(i));
		}
		for(int i = (maxPubs/2); i < ((maxPubs/4) + (maxPubs/2)); i++) {
			res3.add(pubs.get(i));
		}
		for(int i = ((maxPubs/4) + (maxPubs/2)); i < maxPubs; i++) {
			res4.add(pubs.get(i));
		}
		model.addAttribute("pubs1", res1);
		model.addAttribute("pubs2", res2);
		model.addAttribute("pubs3", res3);
		model.addAttribute("pubs4", res4);
		
		if(added)
			return "index";
		else
			return "pubaccount";
    }

	
	@RequestMapping("/addPaymentMethod")
    public String paymentMethod(Model model) {
        model.addAttribute("message", "Hello from Spring MVC Thymeleaf!");
        return "index";
    }
	
	public void addPubs() {
		
		for(int i = 0; i < 100; i++) {
			Pub p = new Pub();
			p.setId(getMaxPubId() + 1);
			p.setName("PUB NAME AVONDHU" + i);
			p.setDesc("Party" + i);
			p.setBio("COME IN" + i);
			p.setImage("avondhu.jpg");
			pubs.add(p);
		}
		
		
	}
	
}
