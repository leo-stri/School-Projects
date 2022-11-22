package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Customer_interface.product;

public class UserInfo {

	private String userId;

	private String password;

	private String number;

	private String address;

	private String sex;

	private double balance;
	
	public Map<product, Integer> cart = new HashMap<product, Integer>(); //a Map object to store the items in the user's shopping cart and corresponding quantities
	public ArrayList<transaction> history = new ArrayList<transaction>(); //an ArrayList object to store all the user's history transactions

	public UserInfo(String userId, String password, String number,
			String address, String sex, double balance) {
		this.userId = userId;
		this.password = password;
		this.number = number;
		this.address = address;
		this.sex = sex;
		this.balance = balance;
	}

	public UserInfo() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
