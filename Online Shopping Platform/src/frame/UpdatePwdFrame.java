/* This UpdatePwdFrame program is to create a frame called ¡°Update password¡±. When password and password confirmation are the same and click the ¡°Submit¡± button, change password successfully and  go to the UserLoginFrame. Use this new password, user can log in the system.
 *  Author: Duchess, Liu Shutong
 *  Student Number: BC004629
 *  Dat
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
import javax.swing.WindowConstants;

import model.UserInfo;

public class UpdatePwdFrame implements ActionListener {
	//Create frame "Update password".
	JFrame jf = new JFrame("Update password");
	JLabel titleLabel = new JLabel("Update password", JLabel.CENTER);
	//Create a Label with text "New password".
	JLabel newLabel = new JLabel("New password");
	JPasswordField newField = new JPasswordField(30);
	//Create a Label with text "Confirm password".
	JLabel confirmLabel = new JLabel("Confirm password");
	JPasswordField confirmField = new JPasswordField(30);
	//Create a button with text "Submit".
	JButton submitBtn = new JButton("Submit");
	//Create a button with text "Back".
	JButton backBtn = new JButton("Back");
	UserInfo userInfo;

	public UpdatePwdFrame(UserInfo userInfo) {
		this.userInfo = userInfo;
		//The frame is 400 pixels wide, 240 pixels high.
		jf.setSize(400, 240);
		//Set BorderLayout with horizontal gap 5 and vertical gap 10.
		jf.setLayout(new BorderLayout(5,10));
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		////Create panelNorth, panelCenter and panelSouth.
		JPanel panelNorth = new JPanel();
		//Set GridLayout, 2 rows, 2 columns, and gaps 10 between components 
		//horizontally and 20 vertically.
		JPanel panelCenter = new JPanel(new GridLayout(2, 2, 10, 20));
		JPanel panelSouth = new JPanel(new BorderLayout());
		//set font.
		Font font = new Font("", Font.BOLD, 20);
		titleLabel.setFont(font);
		//add label to panelNorth.
		panelNorth.add(titleLabel);
		//add label and field to panelCenter.
		panelCenter.add(newLabel);
		panelCenter.add(newField);
		panelCenter.add(confirmLabel);
		panelCenter.add(confirmField);
		//Set GridLayout, 2 rows, 1 column, and gaps 30 between components 
		//horizontally and 10 vertically.
		JPanel southPanel = new JPanel(new GridLayout(2, 1, 30, 10));
		//add submit button and back button to southPanel.
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
		new UpdatePwdFrame(userInfo);
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		//If click submit button
		if (button == submitBtn) {
			//get password, confirmPassword
			String password = new String(newField.getPassword());
			String confirmPassword = new String(confirmField.getPassword());
			//If there is no password
			if (password == null || "".equals(password)) {
				JOptionPane.showMessageDialog(null,
						"new password can not be null!");
				//return to last page and fulfill the information.
				return;
			}
			//If password and confirm password are not the same
			if (!password.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(null,
						"Two password input is not the same!");
				//return to last page and correct the information.
				return;
			}
			userInfo.setPassword(confirmPassword);
			JOptionPane.showMessageDialog(null,
					"Change password successful!");
			jf.dispose();
			new UserLoginFrame();
		}
		//If click the back button
		else if (button == backBtn) {
			jf.dispose();
			//just dispose.
		}
	}
}