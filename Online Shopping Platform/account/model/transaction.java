/*
 * Transaction is an object type that stores information regarding the user's purchasing transactions.
 */

package model;

import java.util.Calendar;

import Customer_interface.product;

public class transaction {
	public String ID;
	public UserInfo customer;
	public String address;
	public String itemName;
	public int quantity;
	public double price;
	public double total;
	public Calendar time;
	
	static int idCount = 0;
	final static int idNum = 5;
	
	public transaction(UserInfo buyer, product item, int quantity){
		ID = generateId();
		customer = buyer;
		address = buyer.getAddress();
		itemName = item.getProductName();
		this.quantity = quantity;
		price = item.getPrice();
		total = price * quantity;
		time = Calendar.getInstance();
	}
	
	//generate a transaction Id of 5 digits, starting from 00000 auto-incrementing
	public static String generateId(){
		String idBase = Integer.toString(idCount++);
		String id = "";
		int zeroes = idNum - idBase.length();
		for (int i = 0; i < zeroes; i++){
			id += "0";
		}
		id += idBase;
		return id;
	}
}
