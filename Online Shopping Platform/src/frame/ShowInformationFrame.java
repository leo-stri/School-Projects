/* This ShowinformationFrame program is to create a frame called ¡°User information¡±. It can show all the information that has been registered. When click ¡°Back¡± button, it can go back to MyAccount frame.
 *  Author: Duchess, Liu Shutong
 *  Student Number: BC004629
 *  Date: 5 May, 2022
 */


package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.UserInfo;

public class ShowInformationFrame implements ActionListener {
	//Create frame "User Information".
	JFrame jf = new JFrame("User Information");
	JLabel titleLabel = new JLabel("User Information", JLabel.CENTER);
	//Create a Label with text "Login ID".
	JLabel nameLabel = new JLabel("Login ID");
	JTextField nameField = new JTextField(30);
	//Create a Label with text "Password"
	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordField = new JPasswordField(30);
	//Create a Label with text "Telephone number".
	JLabel numberLabel = new JLabel("Telephone number");
	JTextField numberField = new JTextField(30);
	//Create a Label with text "Preferred delivery address".
	JLabel addressLabel = new JLabel("Preferred delivery address");
	JTextField addressField = new JTextField(30);
	//Create a Label with text "Sex".
	JLabel sexLabel = new JLabel("Sex");
	JTextField sexField = new JTextField(30);
	//Create a button with text "Close".
	JButton backBtn = new JButton("Close");
	UserInfo userInfo;
	
	public ShowInformationFrame(UserInfo userInfo) {
		this.userInfo = userInfo;
		//The frame is 400 pixels wide, 400 pixels high.
		jf.setSize(400, 400);
		jf.setLayout(new BorderLayout());

		JPanel panelNorth = new JPanel(new BorderLayout());
		//Set GridLayout, 5 rows, 2 columns, and gaps 10 between components 
		//horizontally and 20 vertically.
		JPanel panelCenter = new JPanel(new GridLayout(5, 2, 10, 20));
		JPanel panelSouth = new JPanel(new BorderLayout());
		//set font.
		Font font = new Font("", Font.BOLD, 20);
		titleLabel.setFont(font);
		//add label to north panel.
		panelNorth.add(titleLabel, BorderLayout.CENTER);
		//add label and field to center panel.
		panelCenter.add(nameLabel);
		panelCenter.add(nameField);
		panelCenter.add(passwordLabel);
		panelCenter.add(passwordField);
		panelCenter.add(numberLabel);
		panelCenter.add(numberField);
		panelCenter.add(addressLabel);
		panelCenter.add(addressField);
		panelCenter.add(sexLabel);
		panelCenter.add(sexField);
		//get UserId, Password, Number, Address, Sex.
		nameField.setText(userInfo.getUserId());
		passwordField.setText(userInfo.getPassword());
		numberField.setText(userInfo.getNumber());
		addressField.setText(userInfo.getAddress());
		sexField.setText(userInfo.getSex());
		// can not be editable.
		nameField.setEditable(false);
		passwordField.setEditable(false);
		numberField.setEditable(false);
		addressField.setEditable(false);
		sexField.setEditable(false);
		//Set GridLayout, 1 row, 1 column, and gaps 30 between components 
		//horizontally and 10 vertically.
		JPanel southPanel = new JPanel(new GridLayout(1, 1, 30, 10));
		//add back button to south panel.
		southPanel.add(backBtn);
		panelSouth.add(southPanel, BorderLayout.NORTH);
		//add panelNorth, panelCenter, panelSouth to frame.
		jf.add(panelNorth, BorderLayout.NORTH);
		jf.add(panelCenter, BorderLayout.CENTER);
		jf.add(panelSouth, BorderLayout.SOUTH);
		//The window is in the center of the screen.
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		//register actionListener
		backBtn.addActionListener(this);
	}

	public static void main(String[] args) {
		AllData.initData();
		List<UserInfo> userInfoList = AllData.getUserInfoList();
		UserInfo userInfo = userInfoList.get(0);
		new ShowInformationFrame(userInfo);
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		//If click back button
		if (button == backBtn) {
			jf.dispose();
			//go to MyAccount.
			new MyAccount();
		}
	}
}