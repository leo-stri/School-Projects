/* Creates interface for category "iPhone" */

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

public class iPhone implements ActionListener {
	JFrame frame = new JFrame();
	JButton cart, account,logout,back,iphone13mini,iphone13,iphone13pro,iphone13promax;
	JLabel welcome;
	AllData all;
     public iPhone(){
    	 
    	 
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
			JLabel categories = new JLabel("Categories: iPhone");
			categories.setBounds(140, 80, 300, 250);
			categories.setSize(150,50);
			panel.add(categories);
			
			//Button Back
			back = new JButton("Back");
			back.setBounds(30, 80, 300, 250);
			back.setSize(100,40);
			panel.add(back); 
		
		//Product 1 iphone13mini
		JLabel inchmini =new JLabel("5.4 inch");
		inchmini.setBounds(25, 155, 300, 250);
		panel.add(inchmini);
		
		Icon phonemini = new ImageIcon("iPhone 13 mini (256GB).jpeg");
		JLabel iphoneminiimg = new JLabel(phonemini);
		iphoneminiimg.setBounds(20, 140, 300, 250);
		iphoneminiimg.setSize(250,150);
		panel.add(iphoneminiimg);
		//iPhone13mini button
		iphone13mini = new JButton("iPhone 13 mini (256GB)");
		iphone13mini.setBounds(20, 300, 300, 250);
		iphone13mini.setSize(250,40);
		panel.add(iphone13mini);
		
		//Product 2 iphone13
		JLabel inch13 =new JLabel("6.1 inch");
		inch13.setBounds(285, 155, 300, 250);
		panel.add(inch13);
		
		Icon phone13 = new ImageIcon("iPhone 13 (512GB).jpeg");
		JLabel iphone13img = new JLabel(phone13);
		iphone13img.setBounds(280, 140, 300, 250);
		iphone13img.setSize(250,150);
		panel.add(iphone13img);

		iphone13 = new JButton("iPhone 13 (512GB)");
		iphone13.setBounds(280, 300, 300, 250);
		iphone13.setSize(250,40);
		panel.add(iphone13);
		
		//Product 3 iphone13pro
		JLabel inchpro =new JLabel("6.1 inch");
		inchpro.setBounds(545, 155, 300, 250);
		panel.add(inchpro);
		
		Icon phonepro = new ImageIcon("iPhone 13 Pro (512GB).jpeg");
		JLabel iphoneproimg = new JLabel(phonepro);
		iphoneproimg.setBounds(540, 140, 300, 250);
		iphoneproimg.setSize(250,150);
		panel.add(iphoneproimg);

		iphone13pro = new JButton("iPhone 13 Pro (512GB)");
		iphone13pro.setBounds(540, 300, 300, 250);
		iphone13pro.setSize(250,40);
		panel.add(iphone13pro);
		
		//Product 4 iphone13promax
		JLabel inchpromax =new JLabel("6.7 inch");
		inchpromax.setBounds(25, 395, 300, 250);
		panel.add(inchpromax);
		
		Icon phonepromax = new ImageIcon("iPhone 13 Pro Max (512GB).jpeg");
		JLabel iphonepromaximg = new JLabel(phonepromax);
		iphonepromaximg.setBounds(20, 380, 300, 250);
		iphonepromaximg.setSize(250,150);
		panel.add(iphonepromaximg);

		iphone13promax = new JButton("iPhone 13 Pro Max (512GB)");
		iphone13promax.setBounds(20, 540, 300, 250);
		iphone13promax.setSize(250,40);
		panel.add(iphone13promax);
		
		frame.getContentPane().add(panel);
		
		frame.setTitle("System");
		frame.setSize(830,650);
		frame.setLocationRelativeTo(null);//center frame location
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//register actionListener
		iphone13.addActionListener(this);
		iphone13mini.addActionListener(this);
		iphone13pro.addActionListener(this);
		iphone13promax.addActionListener(this);

		cart.addActionListener(this);
		account.addActionListener(this);
		logout.addActionListener(this);
		back.addActionListener(this);
		
}
 	public void actionPerformed(ActionEvent e) {
 		if (e.getSource()==iphone13||e.getSource()==iphone13mini||e.getSource()==iphone13pro||e.getSource()==iphone13promax) {
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
		}else if (e.getSource() == cart){
			JFrame frame = new ShoppingCart();
	    	frame.setLocationRelativeTo(null);//center frame location
		}else if (e.getSource() == cart){
			JFrame frame = new ShoppingCart();
	    	frame.setLocationRelativeTo(null);//center frame location
		}else{
			frame.dispose();
			CustomerMainmenu frame = new CustomerMainmenu();

 		}
		
		
	}


}
