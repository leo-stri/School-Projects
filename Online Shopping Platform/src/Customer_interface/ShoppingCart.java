/*
 * Shopping Cart is a class that implements all relevant functions regarding the item 
 * purchasing action of the users, from adding items to shopping cart to checking out 
 * and generating receipt. 
 */

package Customer_interface;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import frame.AllData;
import model.UserInfo;
import model.transaction;

public class ShoppingCart extends JFrame implements ActionListener{
	double total = 0;
	JLabel totalPayment = new JLabel("Total payment: ");
	JButton checkOut = new JButton("CheckOut");
	JButton clear = new JButton("Clear");
	
	//ArrayLists to correspond index with datas
    ArrayList<product> holdingProducts = new ArrayList<product>();
    ArrayList<Integer> quantities = new ArrayList<Integer>();
    ArrayList<JButton> increButtons = new ArrayList<JButton>();
    ArrayList<JButton> decreButtons = new ArrayList<JButton>();
    ArrayList<JButton> removeButtons = new ArrayList<JButton>();
    ArrayList<JLabel> quantityLabels = new ArrayList<JLabel>();
    ArrayList<JLabel> totalLabels = new ArrayList<JLabel>();
    ArrayList<JPanel> productPanels = new ArrayList<JPanel>();
    UserInfo user = AllData.getCurrentUser();
    JFrame checkoutFrame;//variable to store checkout frame that will pop out when user checks out
    
	public ShoppingCart(){
		
		UserInfo user = AllData.getCurrentUser();
		
		//Window creation consideration
		setLayout(new BorderLayout());
		setTitle("Shopping Cart");      
	    setVisible(true);
	    setLocationRelativeTo(null);
	   
	    //Loop through the products in the shopping cart to create each product interface
	    JPanel productPanel = new JPanel(new GridLayout(7,1,0,10));
	    Dimension labelSize = new Dimension(150, 60);
	    Dimension btnSize = new Dimension(100,50);
	    
	    checkOut.addActionListener(this);
	    clear.addActionListener(this);
	    
	    JPanel panel = new JPanel(new GridLayout());
    	panel.add(new JLabel("Product Name", SwingConstants.CENTER));
    	panel.add(new JLabel("Image", SwingConstants.CENTER));
    	panel.add(new JLabel("Quantity", SwingConstants.CENTER));
    	panel.add(new JLabel("Price", SwingConstants.CENTER));
    	panel.add(new JLabel("Total payable", SwingConstants.CENTER));
    	panel.add(new JLabel("Increase", SwingConstants.CENTER));
    	panel.add(new JLabel("Decrease", SwingConstants.CENTER));
    	panel.add(new JLabel("Remove", SwingConstants.CENTER));
    	productPanel.add(panel);
	    for (product p : user.cart.keySet()){
	    	panel = new JPanel(new GridLayout());
	    	JLabel nameLabel = new JLabel(p.getProductName(), SwingConstants.CENTER);
	    	nameLabel.setPreferredSize(labelSize);
	    	Icon icon = new ImageIcon(p.getProductName()+".jpeg");
	    	JLabel image = new JLabel(icon, SwingConstants.CENTER);
	    	image.setPreferredSize(new Dimension(250, 100));
	    	JLabel quantLabel = new JLabel(Integer.toString(user.cart.get(p)), SwingConstants.CENTER);
	    	quantLabel.setPreferredSize(labelSize);
	    	JLabel priceLabel = new JLabel("$" + Double.toString(p.getPrice()), SwingConstants.CENTER); 
	    	priceLabel.setPreferredSize(labelSize);
	    	JLabel totalLabel = new JLabel("$" + Double.toString(p.getPrice() * user.cart.get(p)), SwingConstants.CENTER);
	    	totalLabel.setPreferredSize(labelSize);
	    	JButton incre = new JButton("+");
	    	incre.setFont(new Font("Arial", Font.PLAIN, 30));
	    	incre.setPreferredSize(btnSize);
	    	JButton decre = new JButton("-");
	    	decre.setFont(new Font("Arial", Font.PLAIN, 30));
	    	decre.setPreferredSize(btnSize);
	    	JButton remove = new JButton("remove");
	    	remove.setFont(new Font("Arial", Font.PLAIN, 20));
	    	remove.setPreferredSize(btnSize);
	    	
	    	//add to total payment
	    	total += p.getPrice() * user.cart.get(p);
	    	
	    	//add to array to store the data and buttons
	    	holdingProducts.add(p);
	    	decreButtons.add(decre);
	    	increButtons.add(incre);
	    	removeButtons.add(remove);
	    	quantities.add(user.cart.get(p));
	    	productPanels.add(panel);
	    	quantityLabels.add(quantLabel);
	    	totalLabels.add(totalLabel);
	    	incre.addActionListener(this);
	    	decre.addActionListener(this);
	    	remove.addActionListener(this);
	    	
	    	//add to panel
	    	panel.add(nameLabel);
	    	panel.add(image);
	    	panel.add(quantLabel);
	    	panel.add(priceLabel);
	    	panel.add(totalLabel);
	    	panel.add(incre);
	    	panel.add(decre);
	    	panel.add(remove);
	    	productPanel.add(panel);
	    }
	    //calculates total payment
	    totalPayment.setText("Total Payment: $" + Double.toString(total));
	    
	    if (holdingProducts.size() == 0) {
	    	//nothing in cart
	    	JLabel message = new JLabel("You haven't added anything yet!", SwingConstants.CENTER);
	    	message.setFont(new Font("Serif", Font.BOLD, 20));
	    	add(message, BorderLayout.CENTER);
	    	setSize(1000,600);
	    } else {
	    	//something in cart
	    	JPanel buttonPanel = new JPanel();
		    buttonPanel.add(checkOut);
		    buttonPanel.add(clear);
		    
		    add(productPanel, BorderLayout.CENTER);
		    add(buttonPanel, BorderLayout.SOUTH);
		    pack();
	    }
	    
	    
	    
	}
	
	//add an item into the shopping cart, used when a user clicked "add to cart" when browsing items
	public static void addToCart(product shopped, String amountTxt){
		int inventory = shopped.getInventary();
		try {
			int amount = Integer.parseInt(amountTxt);
			UserInfo user = AllData.getCurrentUser();
			if (amount > inventory){
				//not enough inventory
				JOptionPane.showMessageDialog(null, "insufficient inventory!");
			} else if (!user.cart.containsKey(shopped) && user.cart.size() >= 6){
				//reached max products - 6
				JOptionPane.showMessageDialog(null, "Shopping cart already full");
			} else {
				//All clear, proceed
				int currentQuant;
				if (user.cart.get(shopped) == null){
					currentQuant = 0;
				} else{
					currentQuant = user.cart.get(shopped);
				}
				user.cart.put(shopped, currentQuant + amount);
				JOptionPane.showMessageDialog(null, "Added successfully!");
			}
		} 
		catch (NumberFormatException e){
			//data validation
			JOptionPane.showMessageDialog(null, "Invalid amount format!");
		}
		
	}
	
	//When user clicked proceed in checkout interface, check user wallet, complete the transaction
	//, generate receipt and record the transaction in history.
	public void proceed(){
		double balance = user.getBalance();
		String insufficiency = ""; //name of the insufficient item
		//check if inventory is sufficient
		for (product p : user.cart.keySet()){
			int q = user.cart.get(p);
			if (q > p.getInventary()){
				insufficiency = p.getProductName();
				break;
			}
		}
		
		if (insufficiency != ""){
			//there is an insufficient item
			JOptionPane.showMessageDialog(null, insufficiency + " inventory not enough!");
		} else if (balance < this.total){
			//check the user's wallet
			JOptionPane.showMessageDialog(null, "Insufficient Balance!");
		} else {
			//everything checked, proceed
			user.setBalance(balance - total);
			JOptionPane.showMessageDialog(null, "Purchase success!");
			//Iterate over user's shopping cart to generate a piece of receipt for each product purchased
			for (product p : user.cart.keySet()){
				int q = user.cart.get(p);
				transaction tran = new transaction(user, p, q); //records the transaction and store 
				user.history.add(tran);                         //it in the user's history object
				p.setInventary(p.getInventary() - q); //deduct the inventory
			}
			//clear the shopping cart and close after purchase
			for (JPanel pr : productPanels){
				pr.setVisible(false);
			}
			for (product pr : holdingProducts){
				user.cart.remove(pr);
			}
			checkoutFrame.dispose();
			this.dispose();
		}
		
		
		
	}
	
	//implements the checkout function
	public void checkout(){
		JFrame frame = new JFrame();
		checkoutFrame = frame;
		
		//check if there is anything to buy
		if (user.cart.size() == 0){
			JOptionPane.showMessageDialog(null, "Nothing in here!");
		} else{
			//proceed
			//define content panel
			JPanel content = new JPanel(new GridLayout(3,1));
			JLabel wallet = new JLabel("Wallet: " + Double.toString(user.getBalance()), SwingConstants.CENTER);
			JLabel total  = new JLabel("Total Payable: " + Double.toString(this.total), SwingConstants.CENTER);
			JLabel address = new JLabel("Address: " + user.getAddress(), SwingConstants.CENTER);
			wallet.setFont(new Font("Arial", Font.BOLD, 20));
			total.setFont(new Font("Arial", Font.BOLD, 20));
			address.setFont(new Font("Arial", Font.BOLD, 20));
			content.add(wallet);
			content.add(total);
			content.add(address);
			
			//define function buttons
			JPanel buttonPanel = new JPanel();
			JButton proceed = new JButton("Proceed");
			JButton cancel = new JButton("Cancel");
			proceed.addActionListener(this);
			cancel.addActionListener(this);
			buttonPanel.add(proceed);
			buttonPanel.add(cancel);
			
			frame.setLayout(new BorderLayout());
			frame.add(content, BorderLayout.CENTER);
			frame.add(buttonPanel, BorderLayout.SOUTH);
			
			frame.setTitle("Checkout");      
		    frame.setVisible(true);
		    frame.setSize(500,300);
		    frame.setLocationRelativeTo(null);
		}
		
	}
	
	
	public void actionPerformed(ActionEvent e){
		//increase button clicked
		if (increButtons.contains(e.getSource())) {
			int index = increButtons.indexOf(e.getSource());
			int quantity = quantities.get(index);
			product p = holdingProducts.get(index);
			double newTotal = (quantity + 1) * p.getPrice();
			quantities.set(index, quantity + 1);
			user.cart.put(p, quantity + 1);
			quantityLabels.get(index).setText(Integer.toString(quantity + 1));
			totalLabels.get(index).setText("$" + Double.toString(newTotal));
			
			//update grand total
			total += p.getPrice();
			totalPayment.setText("Total Payment: $" + Double.toString(total));
		}
		
		//decrease button clicked
		if (decreButtons.contains(e.getSource())) {
			int index = decreButtons.indexOf(e.getSource());
			int quantity = quantities.get(index);
			product p = holdingProducts.get(index);
			double newTotal = (quantity - 1) * p.getPrice();
			//check if quantity equals 1
			if (quantity > 1) {
				quantities.set(index, quantity - 1);
				user.cart.put(p, quantity - 1);
				quantityLabels.get(index).setText(Integer.toString(quantity - 1));
				totalLabels.get(index).setText("$" + Double.toString(newTotal));
				//update grand total
				total -= p.getPrice();
				totalPayment.setText("Total Payment: $" + Double.toString(total));
			}
		}
		
		//remove button clicked
		if (removeButtons.contains(e.getSource())) {
			int index = removeButtons.indexOf(e.getSource());
			product p = holdingProducts.get(index);
			quantities.set(index, 0);
			user.cart.remove(p);
			productPanels.get(index).setVisible(false);
		}
		
		//checkout button clicked
		if (e.getSource() == checkOut){
			//open checkout interface
			checkout();
		}
		
		//clear button clicked
		if (e.getSource() == clear){
			for (JPanel p : productPanels){
				p.setVisible(false);
			}
			for (product p : holdingProducts){
				user.cart.remove(p);
			}
		}
		
		if (e.getActionCommand() == "Proceed"){
			proceed();
		}
		
		if (e.getActionCommand() == "Cancel"){
			checkoutFrame.dispose();
		}
	}
}
