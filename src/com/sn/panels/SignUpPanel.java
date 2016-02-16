package com.sn.panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.sn.dblayer.DBLayer;
import com.sn.dblayer.DbHelper;
import com.sn.vo.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class SignUpPanel extends SNJPanel {
	private JTextField email;
	private JTextField username;
	private JTextField address;
	private JTextField age;
	private JTextField password;
	private JTextField name;
	private JTextField phone;
	private JLabel message;

	/**
	 * Create the panel.
	 */
	public SignUpPanel() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		setSize(880, 600);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(191, 59, 499, 317);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(105, 95, 82, 14);
		panel_1.add(lblUsername);
		
		JLabel lblName = new JLabel("Email:");
		lblName.setBounds(105, 71, 82, 14);
		panel_1.add(lblName);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(105, 120, 82, 14);
		panel_1.add(lblAddress);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(105, 145, 82, 14);
		panel_1.add(lblAge);
		
		JLabel lblGender = new JLabel("Password:");
		lblGender.setBounds(105, 170, 82, 14);
		panel_1.add(lblGender);
		
		email = new JTextField();
		email.setBounds(193, 68, 209, 20);
		panel_1.add(email);
		email.setColumns(10);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(193, 95, 209, 20);
		panel_1.add(username);
		
		address = new JTextField();
		address.setColumns(10);
		address.setBounds(193, 120, 209, 20);
		panel_1.add(address);
		
		age = new JTextField();
		age.setColumns(10);
		age.setBounds(193, 145, 209, 20);
		panel_1.add(age);
		
		password = new JTextField();
		password.setColumns(10);
		password.setBounds(193, 170, 209, 20);
		panel_1.add(password);
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				signUp();
			}
		});
		btnSignUp.setBounds(193, 251, 89, 23);
		panel_1.add(btnSignUp);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cancel();
			}
		});
		btnCancel.setBounds(292, 251, 89, 23);
		panel_1.add(btnCancel);
		
		JLabel label = new JLabel("Name:");
		label.setBounds(105, 46, 82, 14);
		panel_1.add(label);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(193, 43, 209, 20);
		panel_1.add(name);
		
		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(105, 195, 82, 14);
		panel_1.add(lblPhone);
		
		phone = new JTextField();
		phone.setColumns(10);
		phone.setBounds(193, 195, 209, 20);
		panel_1.add(phone);
		
		message = new JLabel("");
		message.setBounds(150, 194, 209, 14);
		panel_1.add(message);
		
		JLabel lblPleaseEnterThe = new JLabel("Please enter the following information:");
		lblPleaseEnterThe.setBounds(67, 11, 335, 14);
		panel_1.add(lblPleaseEnterThe);
	}

	protected void signUp() {
		User user = new User();
		user.setUsername(username.getText());
		user.setEmail(email.getText());
		user.setAddress(address.getText());
		user.setName(name.getText());
		user.setPassword(password.getText());
		user.setPhone(phone.getText());
		user.setAge(Integer.parseInt(age.getText()));
		
		try {
			DBLayer.registerUser(user);
			this.message.setText("Successfully Registered. Please Login to continue.");
		}
		catch (Exception e) {
			e.printStackTrace();
			this.message.setText(e.getMessage());
		}
	}

	protected void cancel() {
		getRootFrame().showPanel(new PublicMessagesPanel());
	}
}