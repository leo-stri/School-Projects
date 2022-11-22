/* ProductPagePurchase renders an add-to-cart interface when user clicked into each specific product */

package Customer_interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import frame.AllData;

////iPad mini6(256GB) iPad (9th-gen)(256GB),iPad Air5(256GB),iPadPro (5th gen)(256GB)
public class productPagePurchase implements ActionListener {
	  JFrame frame = new JFrame();
	  JButton addcart,add, minus;
	  JTextField amount;
	  static JLabel Name;
	  static JLabel price, inventory;
	  //icon of product image
	  Icon ProductIcon;
	  //Label for displaying an image icon
	  static JLabel ProductImage = new JLabel();
	  //Text area for displaying description
	  static JTextArea jtaDescription = new JTextArea();
	  product shopped = null;
	  //ProductPage GUI
	  public productPagePurchase(String text) {        
	    //New panel1 store the image,detail & description 
	     JPanel panel1=new JPanel();
	     panel1.setLayout(null);  //set the panel layout
	  
	     jtaDescription.setFont(new Font("Serif", Font.PLAIN, 16));
	    // Set lineWrap and wrapStyleWord true for the text area
	     jtaDescription.setLineWrap(true);  //change line
	     jtaDescription.setWrapStyleWord(true);
	     jtaDescription.setEditable(false);
	     jtaDescription.setBounds(20, 380, 30, 25);
	     jtaDescription.setText("Lose your knack for losing things.\r\n"
	  		+ "AirTag is an easy way to keep track of your stuff.\nAttach one to your keys, "
			+ "slip another one in your backpack. And just like that, they’re on your radar in the Find My app. "
			+ "AirTag has your back.\r\n");
	    // Create a scroll pane to hold the text area
	    JScrollPane scrollPane = new JScrollPane(jtaDescription);
	    scrollPane.getViewport().setViewPosition(new Point(0,0));

	    //Set JLabel productName,price,inventory
	    //Name Label
	    Name=new JLabel("iPhone13");
	    Name.setBounds(300, 5, 250, 80);
	    Name.setFont(new Font("SansSerif ", Font.PLAIN,25));
	    
	    //price Label
	    price=new JLabel("Price: $" + "100");
	    price.setBounds(300, 100, 200, 20);
	    price.setFont(new Font("SansSerif ", Font.PLAIN,20));
	    
	    //inventory
	    inventory=new JLabel("Inventary: 10");
	    inventory.setBounds(300, 120, 200, 20);
	    inventory.setFont(new Font("SansSerif ", Font.PLAIN,15));
	    panel1.add(Name); panel1.add(price); panel1.add(inventory);
	    
	    
	    //create button
	    add =new JButton("+");
	    add.setBounds(400, 150, 50, 50);
	    minus=new JButton("-");
	    minus.setBounds(300, 150, 50, 50);
	    addcart=new JButton("Add to shopping cart");
	    addcart.setBounds(300, 210, 160, 60);
	    amount=new JTextField("1",3);
	    amount.setBounds(350, 150, 50, 50);
	    //add button to buttonSet
	    panel1.add(add);panel1.add(amount);panel1.add(minus);panel1.add(addcart);

		ProductIcon = new ImageIcon("AirPods"+".jpeg");
		ProductImage = new JLabel(ProductIcon);
		ProductImage.setBounds(20, 20, 250, 250);
		JLabel Description = new JLabel("Description:");
		Description.setBounds(20,290,150, 40);
		Description.setFont(new Font("SansSerif ", Font.PLAIN,24));
		scrollPane.setBounds(20,320,550, 200);
		panel1.add(Description);
	    panel1.add(ProductImage);
	    panel1.add(scrollPane);

	    //add button to listener
	    add.addActionListener(this);
	    minus.addActionListener(this);
	    addcart.addActionListener(this);
	    frame.getContentPane().add(panel1);
		frame.setTitle("System");
		frame.setSize(600,580);
		frame.setLocationRelativeTo(null);//center frame location
		frame.setVisible(true);
	  }


	  //get the product index from arrayList based on button name
	  public int getIndex(String text) {
		  int index = 0;
			for (int i=0;i<AllData.getProductlist().size();i++) {
				if (AllData.getProductlist().get(i).getProductName().equals(text)) {
					index=i;
					break;
				}
			} 
			
			return index;
		  
	  }
	  
	  public void setDisplay(int index) {
		  setName(index);
		  setInventory(index);
		  setDescription(index);
		  setPrice(index);
		  setImageIcon(index);
		  shopped = AllData.getProductlist().get(index);
	  }
	  	  // Set the image icon 
	  public static void setImageIcon(int index) {
		String imageName=AllData.getProductlist().get(index).getProductName();     
		Icon newProductIcon = new ImageIcon(imageName+".jpeg");
		ProductImage.setIcon(newProductIcon);
	  }
	  
	  
	  
	  // Set product name
	  public static void setName(int index) {
			Name.setText("<html>"+ AllData.getProductlist().get(index).getProductName()+"</html>");
	    }
	  
	  	  // Set product inventory 
	  public static void setInventory(int index) {
		  String newInventary=Integer.toString(AllData.getProductlist().get(index).getInventary());
		  inventory.setText("Inventary: "+newInventary);
	  }
	
	  // Set product description 
	  public static void setDescription(int index) {
				jtaDescription.setText(AllData.getProductlist().get(index).getDescrision());
	  }
	  
	  // Set product price
	  public static void setPrice(int index) {
		    String newPrice=Double.toString(AllData.getProductlist().get(index).getPrice());
			price.setText("Price: $" + newPrice);
	  }
	  
	
	  
	 	public void actionPerformed(ActionEvent e) {
	 		int TextAmount;
	         if(e.getSource()==minus) { 
	        	 TextAmount=Integer.parseInt(amount.getText());
	 		   if (TextAmount>1) {
	 			  TextAmount--;
	 		      amount.setText(Integer.toString(TextAmount));
	 		   }
	         }
	 		 else if(e.getSource()==add) { 
	 			    TextAmount=Integer.parseInt(amount.getText());
		 			TextAmount++;
			 		amount.setText(Integer.toString(TextAmount));
			 		  
	 		 } else if(e.getSource() == addcart){
	 			 String amountTxt = amount.getText();
	 			 ShoppingCart.addToCart(shopped, amountTxt);
	 		 }
	         
			
			
		}
	  
	

	 	
}
