/* This UpdateInformationFrame program is to create a frame called ¡°Update User information¡±. In this frame, user can change their personal information. When user has successfully change their information and click the ¡°submit¡± button, then go to UserloginFrame to log in. When user do not want to change their information and click ¡°Back¡± button, also go back to UserLoginFrame.
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.UserInfo;

public class UpdateInformationFrame implements ActionListener {
	//Create frame "Update User information".
	JFrame jf = new JFrame("Update User information");
	JLabel titleLabel = new JLabel("Update User information", JLabel.CENTER);
	//Create a Label with text "Login ID".
	JLabel nameLabel = new JLabel("Login ID");
	JTextField nameField = new JTextField(30);
	//Create a Label with text "Password".
	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordField = new JPasswordField(30);
	//Create a Label with text "Confirm password".
	JLabel confirmLabel = new JLabel("Confirm password");
	JPasswordField confirmField = new JPasswordField(30);
	//Create a Label with text "Telephone number".
	JLabel numberLabel = new JLabel("Telephone number");
	JTextField numberField = new JTextField(30);
	//Create a Label with text "Preferred delivery address".
	JLabel addressLabel = new JLabel("Preferred delivery address");
	JTextField addressField = new JTextField(30);
	//Create a Label with text "Sex".
	JLabel sexLabel = new JLabel("Sex");
	//Create a combo box with choices male, female.
	JComboBox<String> sexCombox = new JComboBox<String>(new String[] { "male",
			"female" });
	//Create a button with text "Submit".
	JButton submitBtn = new JButton("Submit");
	//Create a button with text "Back".
	JButton backBtn = new JButton("Back");
	UserInfo userInfo;

	public UpdateInformationFrame(UserInfo userInfo) {
		this.userInfo = userInfo;
		//The frame is 400 pixels wide, 400 pixels high.
		jf.setSize(450, 450);
		jf.setLayout(new BorderLayout());
		//Create panelNorth, panelCenter and panelSourth.
		JPanel panelNorth = new JPanel(new BorderLayout());
		//Set GridLayout, 5 rows, 2 columns, and gaps 10 between components 
		//horizontally and 20 vertically.
		JPanel panelCenter = new JPanel(new GridLayout(6, 2, 10, 20));
		JPanel panelSouth = new JPanel(new BorderLayout());
		//set font.
		Font font = new Font("", Font.BOLD, 20);
		titleLabel.setFont(font);
		//add label to panelNorth.
		panelNorth.add(titleLabel, BorderLayout.CENTER);
		//add label, field and combox to panelCenter.
		panelCenter.add(nameLabel);
		panelCenter.add(nameField);
		panelCenter.add(passwordLabel);
		panelCenter.add(passwordField);
		panelCenter.add(confirmLabel);
		panelCenter.add(confirmField);
		panelCenter.add(numberLabel);
		panelCenter.add(numberField);
		panelCenter.add(addressLabel);
		panelCenter.add(addressField);
		panelCenter.add(sexLabel);
		panelCenter.add(sexCombox);
		//get UserId, UserId, Number, Address, Sex. 
		nameField.setText(userInfo.getUserId());
		passwordField.setText(userInfo.getPassword());
		numberField.setText(userInfo.getNumber());
		addressField.setText(userInfo.getAddress());
		sexCombox.setSelectedItem(userInfo.getSex());
		//Set GridLayout, 2 rows, 1 columns, and gaps 30 between components 
		//horizontally and 10 vertically.
		JPanel southPanel = new JPanel(new GridLayout(2, 1, 30, 10));
		//add button to southPanel.
		southPanel.add(submitBtn);
		southPanel.add(backBtn);
		panelSouth.add(southPanel, BorderLayout.NORTH);
		//add panelNorth, panelCenter, panelSouth to frame.
		jf.add(panelNorth, BorderLayout.NORTH);
		jf.add(panelCenter, BorderLayout.CENTER);
		jf.add(panelSouth, BorderLayout.SOUTH);
		//The window is in the center of the screen.
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		//register actionListener.
		submitBtn.addActionListener(this);
		backBtn.addActionListener(this);
	}

	public static void main(String[] args) {
		AllData.initData();
		List<UserInfo> userInfoList = AllData.getUserInfoList();
		UserInfo userInfo = userInfoList.get(0);
		new UpdateInformationFrame(userInfo);
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		//If click submit button
		if (button == submitBtn) {
			//get userID, password, confirm, number.
			String userId = nameField.getText();
			String password = new String(passwordField.getPassword());
			String confirm = new String(confirmField.getPassword());
			String number = numberField.getText();
			//If there is no userID 
			if (userId == null || "".equals(userId)) {
				JOptionPane.showMessageDialog(null, "userId can not be null!");
				//return to last page and fulfill the information.
				return;
			}
			//If there is no password
			if (password == null || "".equals(password)) {
				JOptionPane
						.showMessageDialog(null, "password can not be null!");
				//return to last page and fulfill the information.
				return;
			}
			//If there is no number
			if (number == null || "".equals(number)) {
				JOptionPane.showMessageDialog(null,
						"phone number can not be null!");
				//return to last page and fulfill the information.
				return;
			}
			//If the password confirmation does not equal to password
			if (!confirm.equals(password)) {
				JOptionPane.showMessageDialog(null,
						"Two password input are not the same!");
				//return to last page and check the information.
				return;
			}
			//get address, sex.
			String address = addressField.getText();
			String sex = (String) sexCombox.getSelectedItem();
			JOptionPane.showMessageDialog(null,
					"you have successfully changed your information!");
			userInfo.setUserId(userId);
			userInfo.setPassword(password);
			userInfo.setNumber(number);
			userInfo.setAddress(address);
			userInfo.setSex(sex);
			jf.dispose();
		}
		//If click back button.
		else if (button == backBtn) {
			jf.dispose();
			//go to UserLoginFrame.
			new MyAccount();
		}
	}
}