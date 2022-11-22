/* The product class stores information about each type of product*/

package Customer_interface;

public class product {
	private static int countID;
	private String productName,productDescrision;
	private int productID,inventory,soldQuantity;
	private double productPrice,Applecare;
	private String Category ="none";
	
	public product() {
		countID++;
		productID=countID;
		productName= "Unknown";
		productDescrision= "no descrisions";
		Applecare=0;
		soldQuantity=0;
	}
	
	
	public product(String productName) {
		countID++;
		productID=countID;
		this.productName=productName;
		productDescrision= "";
		Applecare=0;
		soldQuantity=0;
		
	}
	
	public product(String productName,double productPrice,int inventory) {
		countID++;
		productID=countID;
		this.productName=productName;
		this.productPrice=productPrice;
		this.inventory=inventory;
		productDescrision= "";
		Applecare=0;
		soldQuantity=0;
		
	}
	
	public product(String productName, double productPrice,int inventory, String productDescrision) {
		countID++;
		productID=countID;
		this.productName=productName;
		this.productDescrision= productDescrision;
		this.productPrice=productPrice;
		this.inventory=inventory;
		Applecare=0;
		soldQuantity=0;

	}
	
	
	void setDescrision(String descrision) {
		this.productDescrision=descrision;	
	}
	
	void setProductName(String productName) {
		this.productName=productName;	
	}
	
	void setInventary(int inventory) {
		this.inventory=inventory;	
	}
	

	
	public String getProductName(){
		return productName;
		
	}
	
	public int getInventary(){
		return inventory;
		
	}
	
	public String getDescrision(){
		return productDescrision;
		
	}
	
	public double getPrice(){
		return productPrice;
		
	}
	
	public String getCategory() {
		return Category;
	}
	
	
	void AddInventary(int amount){
		inventory+=amount;
		
		
	}
	
	void  SoldProduct(int amount) {
		soldQuantity+=amount;
		ReduceInventary(amount);
	}
	
	void ReduceInventary(int amount){
		inventory-=amount;
		
	}
	

}
