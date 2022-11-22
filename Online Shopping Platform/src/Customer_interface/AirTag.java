/* Creates interface for category "AirTag" */

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

public class AirTag  implements ActionListener {
	
	JFrame frame = new JFrame();
	JButton cart, account,logout,back,airtag1p,airtag4p,keyring;
	JLabel welcome;
	public AirTag() {
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
      		JLabel categories = new JLabel("Categories: AirTag");
      		categories.setBounds(140, 80, 300, 250);
      		categories.setSize(150,50);
      		panel.add(categories);
      		
      		//Button Back
      		back = new JButton("Back");
      		back.setBounds(30, 80, 300, 250);
      		back.setSize(100,40);
      		panel.add(back); 
		
		
		//Product 1 AirTag(1p)
		JLabel inchair =new JLabel("(1 Pack) - MOP 249");
		inchair.setBounds(25, 155, 300, 250);
		panel.add(inchair);
		
		Icon airtag_1p = new ImageIcon("AirTag(1 pack).jpeg");
		JLabel airtag1pimg = new JLabel(airtag_1p);
		airtag1pimg.setBounds(20, 140, 300, 250);
		airtag1pimg.setSize(250,150);
		panel.add(airtag1pimg);
		
		airtag1p = new JButton("AirTag(1 pack)");
		airtag1p.setBounds(20, 300, 300, 250);
		airtag1p.setSize(250,40);
		panel.add(airtag1p);
		
		//Product 2 AirTag(4p)
		JLabel inchpro14 =new JLabel("(4 Pack)  - MOP 849");
		inchpro14.setBounds(285, 155, 300, 250);
		panel.add(inchpro14);
		
		Icon airtag_4p = new ImageIcon("AirTag(4 pack).jpeg");
		JLabel airtag4pimg = new JLabel(airtag_4p);
		airtag4pimg.setBounds(280, 140, 300, 250);
		airtag4pimg.setSize(250,150);
		panel.add(airtag4pimg);

		airtag4p = new JButton("AirTag(4 pack)");
		airtag4p.setBounds(280, 300, 300, 250);
		airtag4p.setSize(250,40);
		panel.add(airtag4p);
		
		//Product 3 AirTag Hermès Key Ring
		JLabel inchpro16 =new JLabel("");
		inchpro16.setBounds(545, 155, 300, 250);
		panel.add(inchpro16);
				
		Icon key = new ImageIcon("AirTag Herm Key Ring.jpeg");
		JLabel keyringimg = new JLabel(key);
		keyringimg.setBounds(540, 140, 300, 250);
		keyringimg.setSize(250,150);
		panel.add(keyringimg);

		keyring = new JButton("AirTag Herm Key Ring");
		keyring.setBounds(540, 300, 300, 250);
		keyring.setSize(250,40);
		panel.add(keyring);
		frame.getContentPane().add(panel); 
		frame.setTitle("System");
		frame.setSize(830,650);
		frame.setLocationRelativeTo(null);//center frame location
	    frame.setVisible(true);
	    
	    //register actionListener
	    airtag1p.addActionListener(this);
	    airtag4p.addActionListener(this);
	    keyring.addActionListener(this);
	    
		cart.addActionListener(this);
		account.addActionListener(this);
		logout.addActionListener(this);
		back.addActionListener(this);

		}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==airtag1p || e.getSource()==keyring || e.getSource()==airtag4p) {
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

