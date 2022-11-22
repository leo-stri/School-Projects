/* Creates interface for category "iPad" */

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

public class iPad implements ActionListener{
	JFrame frame = new JFrame();
	JButton cart, account,logout,back,ipad,ipadair,ipadpro,ipadmini,keyboard,pencil;
	JLabel welcome;
	AllData all;
	
	public iPad() {
		
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
		JLabel categories = new JLabel("Categories: iPad");
		categories.setBounds(140, 80, 300, 250);
		categories.setSize(150,50);
		panel.add(categories);
		
		//Button Back
		back = new JButton("Back");
		back.setBounds(30, 80, 300, 250);
		back.setSize(100,40);
		panel.add(back); 
		
		//"iPad Air5(256GB)","iPad mini6(256GB)", "iPadPro (5th gen)(256GB)", 
		//Product 1 ipadmini
		JLabel inchmini =new JLabel("8.3 inch");
		inchmini.setBounds(25, 155, 300, 250);
		panel.add(inchmini);
		
		Icon padmini = new ImageIcon("iPad mini6(256GB).jpeg");  
		JLabel ipadminiimg = new JLabel(padmini);
		ipadminiimg.setBounds(20, 140, 300, 250);
		ipadminiimg.setSize(250,150);
		panel.add(ipadminiimg);
		
		ipadmini = new JButton("iPad mini6(256GB)");
		ipadmini.setBounds(20, 300, 300, 250);
		ipadmini.setSize(250,40);
		panel.add(ipadmini);
		
		//Product 2  iPad
		JLabel inchpad =new JLabel("10.2 inch");
		inchpad.setBounds(285, 155, 300, 250);
		panel.add(inchpad);
		
		Icon pad = new ImageIcon("iPad (9th-gen) (256GB).jpeg");
		JLabel ipadimg = new JLabel(pad);
		ipadimg.setBounds(280, 140, 300, 250);
		ipadimg.setSize(250,150);
		panel.add(ipadimg);
		//iPad button
		ipad = new JButton("iPad (9th-gen) (256GB)");
		ipad.setBounds(280, 300, 300, 250);
		ipad.setSize(250,40);
		panel.add(ipad);
		
		//Product 3 ipadAir
		JLabel inchair =new JLabel("10.9 inch");
		inchair.setBounds(545, 155, 300, 250);
		panel.add(inchair);
		
		Icon padair = new ImageIcon("iPad Air5(256GB).jpeg");
		JLabel ipadairimg = new JLabel(padair);
		ipadairimg.setBounds(540, 140, 300, 250);
		ipadairimg.setSize(250,150);
		panel.add(ipadairimg);
		//ipadair button
		ipadair = new JButton("iPad Air5(256GB)");
		ipadair.setBounds(540, 300, 300, 250);
		ipadair.setSize(250,40);
		panel.add(ipadair);
		
		//Product 4 ipadpro
		JLabel inchpro =new JLabel("12.9 inch");
		inchpro.setBounds(25, 395, 300, 250);
		panel.add(inchpro);
		
		Icon padpro = new ImageIcon("iPadPro (5th gen)(256GB).jpeg");
		JLabel ipadproimg = new JLabel(padpro);
		ipadproimg.setBounds(20, 380, 300, 250);
		ipadproimg.setSize(250,150);
		panel.add(ipadproimg);
		//ipadpro button
		ipadpro = new JButton("iPadPro (5th gen)(256GB)");
		ipadpro.setBounds(20, 540, 300, 250);
		ipadpro.setSize(250,40);
		panel.add(ipadpro);
		
		//Product 5 keyboard
		Icon board = new ImageIcon("Magic Keyboard for iPad Pro.jpeg");
		JLabel keyboardimg = new JLabel(board);
		keyboardimg.setBounds(280, 380, 300, 250);
		keyboardimg.setSize(250,150);
		panel.add(keyboardimg);
		//keyboard button
		keyboard = new JButton("Magic Keyboard for iPad Pro");
		keyboard.setBounds(280, 540, 300, 250);
		keyboard.setSize(250,40);
		panel.add(keyboard);
		
		
		//Product Apple Pencil
		Icon pen = new ImageIcon("Apple Pencil (2nd generation).jpeg");
		JLabel pencilimg = new JLabel(pen);
		pencilimg.setBounds(540, 380, 250, 150);
		panel.add(pencilimg);
		//pencil button
		pencil = new JButton("Apple Pencil (2nd generation)");
		pencil.setBounds(540, 540, 250, 40);
		panel.add(pencil);
		
		
		
		
		//register actionListener
		cart.addActionListener(this);
		account.addActionListener(this);
		logout.addActionListener(this);
		back.addActionListener(this);
		
		ipad.addActionListener(this);
		ipadair.addActionListener(this);
		ipadpro.addActionListener(this);
		ipadmini.addActionListener(this);
		keyboard.addActionListener(this);
		pencil.addActionListener(this);
		
		frame.getContentPane().add(panel);
		frame.setTitle("System");
		frame.setSize(830,650);
		frame.setLocationRelativeTo(null);//center frame location
	    frame.add(panel); 
	    frame.setVisible(true);
	}
 	public void actionPerformed(ActionEvent e) {
 		//if the source are product buttons(ipad,ipadair,ipadpro,ipadmini,keyboard);
		if (e.getSource()==ipad || e.getSource()==ipadair || e.getSource()==ipadpro|| e.getSource()==ipadmini|| e.getSource()==keyboard|| e.getSource()==pencil) {
			if (AllData.getCurrentUser().getUserId()=="manager") {
	 			productPageModify page=new productPageModify(e.getActionCommand());
	 	 		int i=page.getIndex(e.getActionCommand());
	 	 		page.setDisplay(i);
	 				
	 			}else {
	 			productPagePurchase page=new productPagePurchase(e.getActionCommand());
	 			int i=page.getIndex(e.getActionCommand());
	 			page.setDisplay(i);
	 			}
		}else if (e.getSource()==account) {
			  new MyAccount();
				
		}else if (e.getSource()==logout) {
			   frame.dispose();
			   Functions.logout();
		}
		else if (e.getSource() == cart){
			JFrame frame = new ShoppingCart();
	    	frame.setLocationRelativeTo(null);//center frame location
		}else{frame.dispose();
    	CustomerMainmenu frame = new CustomerMainmenu();


		}

		
	}
	
	

}
