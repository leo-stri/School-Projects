/*
 * Receipt is a class for the receipt interface. It is in "My account" menu triggered by "receipt"
 * button. It shows all the history transaction information of the logged in user in chronological order.
 */

package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

import model.UserInfo;
import model.transaction;

public class receipt extends JFrame{
	JTextArea history = new JTextArea(15, 15); //holds all the history transactions
	UserInfo user = AllData.getCurrentUser();
	ArrayList<transaction> transOrdered = (ArrayList<transaction>)user.history.clone();
	public receipt(){
		Collections.reverse(transOrdered);//sort the transaction list in chronological order
		history.setFont(new Font("Serif", Font.PLAIN, 16));
		history.setLineWrap(true);  //change line
		history.setWrapStyleWord(true);
	    history.setEditable(false);
	    history.setText("Hey Hey Hey");
	    String historyT = "";
	    
	    //put all transaction in history into the textfield
	    for (transaction t : transOrdered){
	    	//concatenating all receipt information into strings
	    	String tran = "ID: ";
	    	tran += t.ID;
	    	tran += "      item: ";
	    	tran += t.itemName;
	    	tran += "      quantity: ";
	    	tran += Integer.toString(t.quantity);
	    	tran += "      price: $";
	    	tran += Double.toString(t.price);
	    	tran += "      total payment: $";
	    	tran += Double.toString(t.total);
	    	tran += "      address: ";
	    	tran += t.address;
	    	tran += "      timestamp: ";
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd 'at' hh:mm:ss");
	    	String time = formatter.format(t.time.getTime());
	    	tran += time;
	    	tran += "\n\n";
	    	historyT += tran;
	    }
	    transOrdered.clear(); //release dummy ArrayList
	    history.setText(historyT);
	    JScrollPane scrollPane = new JScrollPane(history);
	    
	    //show a message if user haven't bought anything yet
	    if (user.history.size() == 0) {
	    	JLabel message = new JLabel("You haven't bought anything yet!", SwingConstants.CENTER);
	    	message.setFont(new Font("Serif", Font.BOLD, 20));
	    	add(message);
	    }else {
		    add(scrollPane);
	    }
	    
	    //frame settings
		setTitle("Receipts");      
	    setVisible(true);
	    setSize(1200,500);
	}
	
}
