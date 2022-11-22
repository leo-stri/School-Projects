/* This class renders an interface for customer menu, it provides selection of various product types */

package Customer_interface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import frame.AllData;
import frame.MyAccount;
import frame.UserLoginFrame;
import model.Functions;
import model.UserInfo;

public class CustomerMainmenu implements ActionListener{
	AllData all;
	JFrame frame = new JFrame();
	JButton cart,account,logout,iphone,ipad,airpods,macbook,airtag;
	JLabel welcome;

	public CustomerMainmenu() {
		JPanel panel = new JPanel();
		panel.setLayout(null); 
		
		//4 main button
		Font Menufont=new Font("SansSerif ", Font.PLAIN,20);
		//Welcome message
		welcome = new JLabel("Welcome, " + AllData.getCurrentUser().getUserId());
		welcome.setFont(new Font("Serif", Font.BOLD, 20));
		welcome.setBounds(20, 20, 180, 40);
	    panel.add(welcome); 

		//Button Shopping Cart
		cart = new JButton("Shopping Cart");
		cart.setBounds(220, 20, 180, 40);
		cart.setFont(Menufont);
	    panel.add(cart); 
		
		//Button My Account
		account = new JButton("My Account");
		account.setBounds(420, 20, 180, 40);
		account.setFont(Menufont);
	    panel.add(account); 
		
		//Button Logout
		logout = new JButton("Logout");
		logout.setBounds(620, 20, 180, 40);
		logout.setFont(Menufont);
	    panel.add(logout); 
	    panel.setPreferredSize(new Dimension(800, 50));
	    
	    
	  //Label Categories
	  		JLabel categories = new JLabel("Categories:");
	  		categories.setFont(new Font("SansSerif ", Font.BOLD,25));
	  		categories.setBounds(20, 80, 150,70);
	  		panel.add(categories);
	  		
	  		//Category 1
	  		Icon phone = new ImageIcon("Phone.jpeg");
	  		JLabel iphoneimg = new JLabel(phone);
	  		iphoneimg.setBounds(20, 140, 250,150);
	  		panel.add(iphoneimg);
	  	
	  	//iPhone button	
	  		iphone = new JButton("iPhone");
	  		iphone.setBounds(20, 300, 250,40);
	  		panel.add(iphone);
	  		
	  		//Category 2
	  		Icon pad = new ImageIcon("Pad.jpeg");
	  		JLabel ipadimg = new JLabel(pad);
	  		ipadimg.setBounds(280, 140, 250,150);
	  		panel.add(ipadimg);
	  		
	  	//iPad button	
	  		ipad = new JButton("iPad");
	  		ipad.setBounds(280, 300, 250,40);
	  	//	ipad.setSize(250,40);
	  		panel.add(ipad);
	  				
	  		//Category 3
	  		Icon mac = new ImageIcon("Macbook.jpeg");
	  		JLabel macimg = new JLabel(mac);
	  		macimg.setBounds(540, 140, 250,150);
	  	//	macimg.setSize(250,150);
	  		panel.add(macimg);
	  	//macbook button
	  		macbook = new JButton("Macbook");
	  		macbook.setBounds(540, 300, 250,40);
	  	//	macbook.setSize(250,40);
	  		panel.add(macbook);
	  		
	  		
	  		//Category 4 AirPods
	  		Icon pods = new ImageIcon("Airpods.jpeg");
	  		JLabel podsimg = new JLabel(pods);
	  		podsimg.setBounds(20, 380,250,150);
	  		panel.add(podsimg);
	  	    //AirPods button
	  		airpods = new JButton("Airpods");
	  		airpods.setBounds(20, 540, 250,40);
	  		panel.add(airpods);
	  		
	  		//Category 5 AirTag 
	  		Icon tag = new ImageIcon("Airtag.jpeg");
	  		JLabel tagimg = new JLabel(tag);
	  		tagimg.setBounds(280, 380, 250,150);
	  		panel.add(tagimg);
	  		//AirTag button
	  		airtag = new JButton("Airtag");
	  		airtag.setBounds(280, 540,250,40);
	  		//airtag.setSize(250,40);
	  		panel.add(airtag); 
	  		
	  		//register actionListner
	  		iphone.addActionListener(this);
	  		macbook.addActionListener(this);
	  		airpods.addActionListener(this);
	  		ipad.addActionListener(this);
	  		airtag.addActionListener(this);
	  		cart.addActionListener(this);
	  		account.addActionListener(this);
	  		logout.addActionListener(this);
	  		
	  		
	  	    //add panel to the frame
	        frame.getContentPane().add(panel);
	        //display frame
			frame.setTitle("System");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(830,650);
			frame.setLocationRelativeTo(null);//center frame location
			frame.setVisible(true);

	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==iphone){
		   frame.dispose();
    	   iPhone frame = new iPhone();	
		}else if (e.getSource()==macbook) {
			   frame.dispose();
			   Macbook frame = new Macbook();	
			
		}else if (e.getSource()==ipad) {
			   frame.dispose();
	    	   iPad frame = new iPad();	
			
		}else if (e.getSource()==airtag) {
			   frame.dispose();
	    	   AirTag frame = new AirTag();	
			
		}else if (e.getSource()==airpods) {
			   frame.dispose();
	    	   AirPods frame = new AirPods();	
	    	   
		}else if (e.getSource()==cart) {
	    	   JFrame frame = new ShoppingCart();
	    	   frame.setLocationRelativeTo(null);//center frame location
			
		}else if (e.getSource()==account) {
			  new MyAccount();
			
		}else if (e.getSource()==logout) {
			   frame.dispose();
			   Functions.logout();
		}
		
		
		
	
		
	}


	

		

	
	




}

