/* Creates interface for category "MacBook" */

package Customer_interface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frame.AllData;
import frame.MyAccount;
import frame.UserLoginFrame;
import model.Functions;
import model.UserInfo;

public class Macbook  implements ActionListener {
	
	JFrame frame = new JFrame();
	JButton cart, account,logout,back,macbookair,macbook13,macbook14,macbook16;
	JLabel welcome;
	public Macbook() {
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
      		JLabel categories = new JLabel("Categories: Macbook");
      		categories.setBounds(140, 80, 300, 250);
      		categories.setSize(150,50);
      		panel.add(categories);
      		
      		//Button Back
      		back = new JButton("Back");
      		back.setBounds(30, 80, 300, 250);
      		back.setSize(100,40);
      		panel.add(back); 
		
		//Product 1 Macbook Air 13.3 inch (256GB)
		JLabel inchair =new JLabel("13.3 inch");
		inchair.setBounds(25, 155, 300, 250);
		panel.add(inchair);
		
		Icon macair = new ImageIcon("Macbook Air (256GB).jpeg");
		JLabel macbookairimg = new JLabel(macair);
		macbookairimg.setBounds(20, 140, 300, 250);
		macbookairimg.setSize(250,150);
		panel.add(macbookairimg);
		
		macbookair = new JButton("Macbook Air (256GB)");
		macbookair.setBounds(20, 300, 300, 250);
		macbookair.setSize(250,40);
		panel.add(macbookair);
		
		//Product 2 Macbook Pro 14 inch (512GB)
		JLabel inchpro14 =new JLabel("14 inch");
		inchpro14.setBounds(285, 155, 300, 250);
		panel.add(inchpro14);
		
		Icon mac14 = new ImageIcon("Macbook Pro 14 inch (512GB).jpeg");
		JLabel macbook14img = new JLabel(mac14);
		macbook14img.setBounds(280, 140, 300, 250);
		macbook14img.setSize(250,150);
		panel.add(macbook14img);

		macbook14 = new JButton("Macbook Pro 14 inch (512GB)");
		macbook14.setBounds(280, 300, 300, 250);
		macbook14.setSize(250,40);
		panel.add(macbook14);
		
		//Product 3 Macbook Pro 16 inch (512GB)
		JLabel inchpro16 =new JLabel("16 inch");
		inchpro16.setBounds(545, 155, 300, 250);
		panel.add(inchpro16);
				
		Icon mac16 = new ImageIcon("Macbook Pro 16 inch (512GB).jpeg");
		JLabel macbook16img = new JLabel(mac16);
		macbook16img.setBounds(540, 140, 300, 250);
		macbook16img.setSize(250,150);
		panel.add(macbook16img);
        //macbook16 button
		macbook16 = new JButton("Macbook Pro 16 inch (512GB)");
		macbook16.setBounds(540, 300, 300, 250);
		macbook16.setSize(250,40);
		panel.add(macbook16);
		
		//product 4 Macbook Pro 13 inch (512GB)
		JLabel inchmac13 =new JLabel("13 inch");
		inchmac13.setBounds(25, 395, 300, 250);
		panel.add(inchmac13);
		
		Icon mac13 = new ImageIcon("Macbook Pro 13 inch (512GB).jpeg");
		JLabel mac13img = new JLabel(mac13);
		mac13img.setBounds(20, 380, 300, 250);
		mac13img.setSize(250,150);
		panel.add(mac13img);
		
		macbook13 = new JButton("Macbook Pro 13 inch (512GB)");
		macbook13.setBounds(20, 540, 250, 40);
		panel.add(macbook13);
		
		
		
		frame.getContentPane().add(panel); 
		frame.setTitle("System");
		frame.setSize(830,650);
		frame.setLocationRelativeTo(null);//center frame location
	    frame.setVisible(true);
	    
	    //register actionListener
		cart.addActionListener(this);
		account.addActionListener(this);
		logout.addActionListener(this);
		back.addActionListener(this);
		
		macbookair.addActionListener(this);
		macbook13.addActionListener(this);
		macbook14.addActionListener(this);
		macbook16.addActionListener(this);

		}



	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==macbookair || e.getSource()==macbook14 || e.getSource()==macbook13|| e.getSource()==macbook16) {
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
		}else{
		frame.dispose();
    	CustomerMainmenu frame = new CustomerMainmenu();


		}
		
	}
	
}

