/* This MyAccount program is to create a frame called ¡°My account¡±.There are 6 buttons in this frame: ¡°Show balance¡±, ¡°Withdraw¡±, ¡°Show Information¡±, ¡°Update Information¡±, ¡°Exit¡± and ¡°Receipts¡±. When click button, It can go to relative frame.
 *  Author: Duchess, Liu Shutong
 *  Student Number: BC004629
 *  Date: 5 May, 2022
 */


package frame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import model.UserInfo;

public class MyAccount implements ActionListener {
    //Create frame "My account".
	JFrame jf = new JFrame("My account");
	//Create 6 buttons.
	JButton btnA = new JButton("Show balance");
	JButton btnB = new JButton("Charge");
	JButton btnC = new JButton("Show Information");
	JButton btnD = new JButton("Update Information");
	JButton btnE = new JButton("Exit");
	JButton receipt = new JButton("Receipts");
	
	public MyAccount() {
		//The frame is 550 pixels wide, 300 pixels high.
		jf.setSize(550, 300);
		//Set GridLayout, 5 rows, 1 column, and gaps 50 between components 
		//horizontally and vertically.
		jf.setLayout(new GridLayout(5, 1, 50, 30));
		//Set font.
		Font font = new Font("", Font.BOLD, 20);
		btnA.setFont(font);
		btnB.setFont(font);
		btnC.setFont(font);
		btnD.setFont(font);
		btnE.setFont(font);
		receipt.setFont(font);
		//add button to frame.
		jf.add(btnA);
		jf.add(btnB);
		jf.add(btnC);
		jf.add(btnD);
		jf.add(btnE);
		jf.add(receipt);
		//register actionListener
		btnA.addActionListener(this);
		btnB.addActionListener(this);
		btnC.addActionListener(this);
		btnD.addActionListener(this);
		btnE.addActionListener(this);
		receipt.addActionListener(this);
		//The window is in the center of the screen.
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}

	public static void main(String[] args) {
		AllData.initData();
		List<UserInfo> userInfoList = AllData.getUserInfoList();
		UserInfo userInfo = userInfoList.get(0);
		new MyAccount();
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		UserInfo userInfo = AllData.getCurrentUser();
		//If click button A
		if (button == btnA) {
			JOptionPane.showMessageDialog(null,
					"your balance is " + userInfo.getBalance());
		}
		//If click button B
		else if (button == btnB) {
			String balanceStr = JOptionPane
					.showInputDialog("Please input charge balance :");
			try {
				double balance = Double.valueOf(balanceStr);
				//If balance < 0
				if (balance < 0) {
					JOptionPane.showMessageDialog(null,
							"Amount must be a positive number!");
					//return to the last page and input a positive number.
					return;
				}
				//If balance % 100 != 0
				if (balance % 100 != 0) {
					JOptionPane.showMessageDialog(null,
							"Amount must be 100 integer times!");
					//return to the last page and input a correct amount.
					return;
				}
				userInfo.setBalance(userInfo.getBalance() + balance);
				JOptionPane.showMessageDialog(
						null,
						"Changed successfully!Your total Money is "
								+ userInfo.getBalance());
			} catch (Exception e2) {
						}
		} 
		//If click button C
		else if (button == btnC) {
			jf.dispose();
			//go to the ShowInformationFrame.
			new ShowInformationFrame(userInfo);
		} 
		//If click button D
		else if (button == btnD) {
			jf.dispose();
			//go to the UpdateInformationFrame.
			new UpdateInformationFrame(userInfo);
		} 
		//If click button E
		else if (button == btnE) {
			jf.dispose();
			//just dispose.
		}
		//If click button receipt
		if (button == receipt){
			JFrame frame = new receipt();
			frame.setLocationRelativeTo(null);
		}
	}
}
