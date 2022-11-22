/* Creates interface for category "AirPods" */

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

public class AirPods  implements ActionListener {
	
	JFrame frame = new JFrame();
	JButton cart, account,logout,back,airpodspro,airpods3,airpods2,airpodsmax;
	JLabel welcome;
	AllData all;
	public AirPods() {
		JPanel panel = new JPanel();	
        panel.setLayout(null);
		
		//4 main button
		Font Menufont=new Font("SansSerif ", Font.PLAIN,20);
		//welcome message
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
      		JLabel categories = new JLabel("Categories: AirPods");
      		categories.setBounds(140, 80, 300, 250);
      		categories.setSize(150,50);
      		panel.add(categories);
      		
      		//Button Back
      		back = new JButton("Back");
      		back.setBounds(30, 80, 300, 250);
      		back.setSize(100,40);
      		panel.add(back); 
		
		
		//Product 1 AirPods Pro		
		Icon airpro = new ImageIcon("AirPods Pro.jpeg");
		JLabel airpodsproimg = new JLabel(airpro);
		airpodsproimg.setBounds(20, 140, 300, 250);
		airpodsproimg.setSize(250,150);
		panel.add(airpodsproimg);
		
		airpodspro = new JButton("AirPods Pro");
		airpodspro.setBounds(20, 300, 300, 250);
		airpodspro.setSize(250,40);
		panel.add(airpodspro);
		
		//Product 2 AirPods (3rd generation)
		Icon air3rd = new ImageIcon("AirPods (3rd generation).jpeg");
		JLabel airpods3img = new JLabel(air3rd);
		airpods3img.setBounds(280, 140, 300, 250);
		airpods3img.setSize(250,150);
		panel.add(airpods3img);

		airpods3 = new JButton("AirPods (3rd generation)");
		airpods3.setBounds(280, 300, 250, 40);
		panel.add(airpods3);
		
		//Product 3 AirPods (2rd generation)
		Icon air2nd = new ImageIcon("AirPods (2rd generation).jpeg");
		JLabel airpods2img = new JLabel(air2nd);
		airpods2img.setBounds(540, 140, 250, 150);
		panel.add(airpods2img);

		airpods2 = new JButton("AirPods (2rd generation)");
		airpods2.setBounds(540, 300, 250, 40);
		panel.add(airpods2);
		
		
		//Product 4 AirPods Max
		Icon airmax = new ImageIcon("AirPods Max.jpeg");
		JLabel airmaximg = new JLabel(airmax);
		airmaximg.setBounds(20, 380, 250, 150);
		panel.add(airmaximg);
		//airpodsmax button 
		airpodsmax = new JButton("AirPods Max");
		airpodsmax.setBounds(20, 540, 250, 40);
		panel.add(airpodsmax);
		
		//display frame
		frame.getContentPane().add(panel); 
		frame.setTitle("System");
		frame.setSize(830,650);
		frame.setLocationRelativeTo(null);//center frame location
	    frame.setVisible(true);
	    
	    //register actionListener
	    airpods3.addActionListener(this);
	    airpods2.addActionListener(this);
	    airpodspro.addActionListener(this);
	    airpodsmax.addActionListener(this);
	    back.addActionListener(this);

		}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==airpods3 || e.getSource()==airpods2 || e.getSource()==airpodspro|| e.getSource()==airpodsmax) {
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
		}else
		{frame.dispose();
    	CustomerMainmenu frame = new CustomerMainmenu();


		}
	}
	
}

