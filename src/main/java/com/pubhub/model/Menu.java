package com.pubhub.model;

import java.util.ArrayList;

public class Menu implements Cloneable{

	ArrayList<Product> menu = new ArrayList<Product>();
	int pubId;
	
    public ArrayList<Product> getMenu() {
		return menu;
	}

	public void setMenu(ArrayList<Product> menu) {
		this.menu = menu;
	}

	public int getPubId() {
		return pubId;
	}

	public void setPubId(int pubId) {
		this.pubId = pubId;
	}
	
	public void addMenu(Product p) {
		menu.add(p);
	}
	
	public void rmMenu(Product p) {
		menu.remove(p);
	}

	 @Override
	 public Object clone() throws CloneNotSupportedException {
	 return super.clone();
	 }
	
}
