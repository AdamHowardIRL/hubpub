package com.pubhub.model;

import java.util.ArrayList;

public class User implements Cloneable{
	int id;
	
	String name;
	String email;
	String password;
	String image;
	String desc;
	String bio;
	float lat;
	float lng;
	String location;
	String address;
	public String isPub;
	ArrayList<Product> cart = new ArrayList<Product>();
	public ArrayList<Order> orders = new ArrayList<Order>();
	
	public ArrayList<Order> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	public void addOrder(Order o) {
		orders.add(o);
	}
	
	public ArrayList<Product> getCart() {
		return cart;
	}
	public void setCart(ArrayList<Product> cart) {
		this.cart = cart;
	}
	public String getIsPub() {
		return isPub;
	}
	public void setIsPub(String isPub) {
		this.isPub = isPub;
	}

	ArrayList<Card> cards = new ArrayList<Card>();
	
	public User() {
		
	}
	public User(int id, String name, String email, String password, String image, String desc, String bio, float lat,
			float lng, String location) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.image = image;
		this.desc = desc;
		this.bio = bio;
		this.lat = lat;
		this.lng = lng;
		this.location = location;
	}
	
	public void addCart(Product c) {
		c.getCartId();
		cart.add(c);
	}
	
	public int genCartId() {
		return cart.size() + 1;
	}
	
	public void rmCart(Product c) {
		cart.remove(c);
	}
	
	public void rmCartId(int id) {
		for(Product p : cart) {
			
		}
	}
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLng() {
		return lng;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String isPub() {
		return isPub;
	}
	
	public String getisPub() {
		return isPub;
	}
	public void setPub(String isPub) {
		this.isPub = isPub;
	}
	
	public void addCard(Card a) {
		cards.add(a);
	}
	public Card getCardById(int id) {
		return cards.get(id);
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}
	 @Override
	 public Object clone() throws CloneNotSupportedException {
	 return super.clone();
	 }
	
}
