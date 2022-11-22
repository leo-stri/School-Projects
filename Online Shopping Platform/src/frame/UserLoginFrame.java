/* This UserLoginFrame program is to create a frame called ¡°User log in¡±. User can log in via  this frame. If click ¡°forget¡± button, it will go to ForgetPwdFrame. If click ¡°register¡± button, it will go to UserRegisterFrame. Besides, we make a difference between manager and customer. If userId equals to ¡°manager¡±, it will go to the ManagerMainmenu. If not, it will go to the CustomerMainmenu.
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import Customer_interface.CustomerMainmenu;
import model.UserInfo;

public class UserLoginFrame implements ActionListener {
	//Create frame "User login in".
	JFrame jf = new JFrame("User login in");
	JLabel titleLabel = new JLabel("User login in", JLabel.CENTER);
	//Create a Label with text "Login ID:".
	JLabel nameLabel = new JLabel("Login ID:");
	JTextField nameField = new JTextField(30);
	//Create a Label with text "Password:".
	JLabel passwordLabel = new JLabel("Password:");
	JPasswordField passwordField = new JPasswordField(30);
	//Create a button with text "Login in".
	JButton loginBtn = new JButton("Login in");
	//Create a button with text "Change or forget password".
	JButton forgetBtn = new JButton("Change or forget password");
	//Create a button with text "Register".
	JButton registerBtn = new JButton("Register");

	public UserLoginFrame() {
		//The field is 100 pixels wide, 40 pixels high.
		nameField.setSize(100, 40);
		//The frame is 400 pixels wide, 300 pixels high.
		jf.setSize(400, 300);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//Set BorderLayout with horizontal gap 5 and vertical gap 20.
		jf.setLayout(new BorderLayout(5,20));
		//Create panelNorth, panelCenter and panelSouth.
		JPanel panelNorth = new JPanel();
		//Set GridLayout, 2 rows, 2 columns, and gaps 6 between components 
		//horizontally and 20 vertically.
		JPanel panelCenter = new JPanel(new GridLayout(2, 2, 6, 20));
		JPanel panelSouth = new JPanel();
		//set font.
		Font font = new Font("", Font.BOLD, 20);
		titleLabel.setFont(font);
		//add label to panelNorth.
		panelNorth.add(titleLabel);
		//add label and field to panelCenter.
		panelCenter.add(nameLabel);
		panelCenter.add(nameField);
		panelCenter.add(passwordLabel);
		panelCenter.add(passwordField);
		nameField.setText("admin");
		passwordField.setText("123456");
		//Set GridLayout, 3 rows, 1 column, and gaps 30 between components 
		//horizontally and 10 vertically.
		JPanel southPanel = new JPanel(new GridLayout(3, 1, 30, 10));
		//add login button, register button and forget button to southPanel.
		southPanel.add(loginBtn);
		southPanel.add(registerBtn);
        southPanel.add(forgetBtn);
	    panelSouth.add(southPanel, BorderLayout.NORTH);
	    //add panelNorth, panelCenter, panelSouth to frame.
		jf.add(panelNorth, BorderLayout.NORTH);
		jf.add(panelCenter, BorderLayout.CENTER);
		jf.add(southPanel, BorderLayout.SOUTH);
		//The window is in the center of the screen.
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		//cannot be resizable.
		jf.setResizable(false);
		//register actionListener.
		loginBtn.addActionListener(this);
		forgetBtn.addActionListener(this);
		registerBtn.addActionListener(this);
	}



	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		//If click login button
		if (button == loginBtn) {
			//get name and password.
			String name = nameField.getText();
			String password = new String(passwordField.getPassword());
			boolean isFound = false;
			List<UserInfo> userInfoList = AllData.getUserInfoList();
			for (int i = 0; i < userInfoList.size(); i++) {
				UserInfo userInfo = userInfoList.get(i);
				//If userID and password equal to that have been registered
				if (userInfo.getUserId().equals(name)
						&& userInfo.getPassword().equals(password)) {
					isFound = true;
					JOptionPane.showMessageDialog(null,
							"user login successful!");
					jf.dispose();
					AllData.setCurrentUser(userInfo);
					//login successful and show my mainmenu
					//go to the CustomerMainmenu.
						CustomerMainmenu frame = new CustomerMainmenu();
					

				}
			}
			//If don't find userId or password
			if (!isFound) {
				JOptionPane.showMessageDialog(null,
						"user id or password is error!");
			}
		} 
		//If click the forget button
		else if (button == forgetBtn) {
			jf.dispose();
			//go to ForgetPwdFrame.
			new ForgetPwdFrame();
		}
		//If click the register button
		else if (button == registerBtn) {
			jf.dispose();
			//go to UserRegisterFrame.
			new UserRegisterFrame();
		}
	}
}