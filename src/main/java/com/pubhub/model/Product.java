package com.pubhub.model;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class Product {
	int id;
	
	char charlie = 'c';
	String name;
	String desc;
	String location;
	float price;
	String image;
	MultipartFile imageData;
	int pubId;
	int cartId;
	public int getCartId() {
		return cartId;
	}



	public void setCartId(int cartId) {
		this.cartId = cartId;
	}



	public int getPubId() {
		return pubId;
	}



	public void setPubId(int pubId) {
		this.pubId = pubId;
	}



	public MultipartFile getImageData() {
		return imageData;
	}



	public void setImageData(MultipartFile imageData) {
		this.imageData = imageData;
	}



	public Product() {
		
	}
	
	

	public Product(String name, String desc, String location, float price) {
		super();
		this.name = name;
		this.desc = desc;
		this.location = location;
		this.price = price;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", desc=" + desc + ", cart=" + cartId + ", price=" + price
				+ ", image=" + image + ", imageData=" + imageData + ", pubId=" + pubId + "]";
	}
	
}
