package com.pubhub.model;

import java.util.Date;

public class Card {
	int id;
	
	String cardHolder;
	String cardNumber;
	int cvv;
	String expiry;
	String time;
	Date now;
	
	String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardHolder() {
		return cardHolder;
	}
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Date getNow() {
		return now;
	}
	public void setNow(Date now) {
		this.now = now;
	}
	
	public Card() {
		
	}
	
	public Card(int id, String cardHolder, String cardNumber, int cvv, String expiry, String time, Date now) {
		super();
		this.id = id;
		this.cardHolder = cardHolder;
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.expiry = expiry;
		this.time = time;
		this.now = now;
	}

	
	
	
}
