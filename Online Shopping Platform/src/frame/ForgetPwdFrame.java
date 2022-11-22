/* This ForgetPwdFrame program is to create a frame called ¡°Change or forget password¡±. When click the ¡°Submit¡± button, if userID and telephone number equal to that have been registered, then go to UpdatePwdFrame. If not, ask user to input again. When click the ¡°back¡± button, go to UserLoginFrame.
 *  Author: Duchess, Liu Shutong
 *  Student Number: BC004629
 *  Date: 5 May, 2022
 *
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
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.UserInfo;

public class ForgetPwdFrame implements ActionListener {

	JFrame jf = new JFrame("Change or forget password");

	JLabel titleLabel = new JLabel("Change or forget password", JLabel.CENTER);

	JLabel nameLabel = new JLabel("User Name");
	JTextField nameField = new JTextField(30);
	JLabel numberLabel = new JLabel("Telephone number");
	JTextField numberField = new JTextField(30);
	JButton submitBtn = new JButton("Submit");

	JButton backBtn = new JButton("Back");

	public ForgetPwdFrame() {
		jf.setSize(400, 300);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.setLayout(new BorderLayout(5,30));

		JPanel panelNorth = new JPanel();
		JPanel panelCenter = new JPanel(new GridLayout(2, 2, 6, 30));
		JPanel panelSourth = new JPanel(new GridLayout(1, 2, 6, 6));

		Font font = new Font("", Font.BOLD, 20);
		titleLabel.setFont(font);

		panelNorth.add(titleLabel);

		panelCenter.add(nameLabel);
		panelCenter.add(nameField);
		panelCenter.add(numberLabel);
		panelCenter.add(numberField);

		JPanel sourthPanel = new JPanel(new GridLayout(2, 1, 30, 10));
		sourthPanel.add(submitBtn);
		sourthPanel.add(backBtn);

		panelSourth.add(sourthPanel, BorderLayout.NORTH);

		jf.add(panelNorth, BorderLayout.NORTH);
		jf.add(panelCenter, BorderLayout.CENTER);
		jf.add(panelSourth, BorderLayout.SOUTH);

		jf.setLocationRelativeTo(null);
		jf.setVisible(true);

		submitBtn.addActionListener(this);
		backBtn.addActionListener(this);
	}

	public static void main(String[] args) {
		AllData.initData();
		new ForgetPwdFrame();
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button == submitBtn) {
			String name = nameField.getText();
			String number = numberField.getText();
			boolean isFund = false;
			List<UserInfo> userInfoList = AllData.getUserInfoList();
			for (int i = 0; i < userInfoList.size(); i++) {
				UserInfo userInfo = userInfoList.get(i);
				if (userInfo.getUserId().equals(name)
						&& userInfo.getNumber().equals(number)) {
					isFund = true;
					new UpdatePwdFrame(userInfo);
					jf.dispose();
					break;
				}
			}
			if (!isFund) {
				JOptionPane.showMessageDialog(null,
						"Wrong information,please input again!");
			}
		} else if (button == backBtn) {
			jf.dispose();
			new UserLoginFrame();
		}
	}
}