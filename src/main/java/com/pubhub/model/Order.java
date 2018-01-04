package com.pubhub.model;

import java.util.ArrayList;
import java.util.Date;

public class Order {

	User customer;
	Card paymentInfo;
	Date time; 
	Pub pub;
	ArrayList<Product> items = new ArrayList<Product>(); 
	String amount;
	int orderNumber;

	String itemStr; 

	String customerName;
	String customerEmail;
	String total;
	String cardNumber;



	public String getItemStr() {
		return itemStr;
	}

	public void setItemStr(String itemStr) {
		this.itemStr = itemStr;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}



	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Order(int order) {
		orderNumber = order;
	}

	public void addItem(Product p) {
		items.add(p);
		itemStr = items.toString();
		amount = Float.toString(totalCart(items));
		total = Float.toString(totalCart(items));
	}

	public float totalCart(ArrayList<Product> res) {
		float total = 0;

		for(Product s : res) {
			total += s.getPrice();
		}
		return total;
	}

	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
		customerName = customer.getName();
		customerEmail = customer.getEmail();
	}
	public Card getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(Card paymentInfo) {
		this.paymentInfo = paymentInfo;
		cardNumber = paymentInfo.getCardNumber();
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Pub getPub() {
		return pub;
	}
	public void setPub(Pub pub) {
		this.pub = pub;
	}
	public ArrayList<Product> getItems() {
		return items;
	}
	public void setItems(ArrayList<Product> items) {
		this.items = items;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}



}
