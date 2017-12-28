package com.pubhub.model;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class Pub implements Cloneable{
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
	boolean isPub;
	ArrayList<Card> cards = new ArrayList<Card>();
	ArrayList<Order> orders = new ArrayList<Order>();
	MultipartFile imageData;
	ArrayList<Product> menu = new ArrayList<Product>();
	
	public int genNewMenuId() {
		return menu.size() + 1;
	}
	
	public void addMenu(Product e) {
		menu.add(e);
	}
	
	public Product getMenuItem(int i) {
		
		Product pe = null;
		for(Product p : menu) {
			if(p.getId() == i)
				return p;
		}
		return pe;
	}
	
	
	public ArrayList<Order> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	public ArrayList<Product> getMenu() {
		return menu;
	}
	public void setMenu(ArrayList<Product> menu) {
		this.menu = menu;
	}
	public MultipartFile getImageData() {
		return imageData;
	}
	public void setImageData(MultipartFile imageData) {
		this.imageData = imageData;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	public Pub() {
		
	}
	public Pub(int id, String name, String email, String password, String image, String desc, String bio, float lat,
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
	
	@Override
	public String toString() {
		return "Pub [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", image=" + image
				+ ", desc=" + desc + ", bio=" + bio + ", lat=" + lat + ", lng=" + lng + ", location=" + location
				+ ", isPub=" + isPub + ", cards=" + cards + ", orders=" + orders + ", menu=" + menu + ", imageData="
				+ imageData + "]";
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
	public boolean isPub() {
		return true;
	}
	public void setPub(boolean isPub) {
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
