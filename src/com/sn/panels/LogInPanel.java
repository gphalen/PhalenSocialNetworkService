package com.sn.panels;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.sn.dblayer.DBLayer;
import com.sn.frames.MainFrame;
import com.sn.vo.Session;
import com.sn.vo.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.Color;

public class LogInPanel extends SNJPanel {
	private JTextField username;
	private JTextField password;
	private JLabel message;
	
	public LogInPanel() {
		setBackground(Color.LIGHT_GRAY);
		setSize(880, 600);
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(153, 103, 559, 259);
		panel.setLayout(null);
		add(panel);
		
		JLabel lbl = new JLabel("Username:");
		lbl.setBounds(77, 83, 91, 14);
		panel.add(lbl);
		
		JLabel label_1 = new JLabel("Password:");
		label_1.setBounds(77, 120, 91, 14);
		panel.add(label_1);
		
		username = new JTextField();
		username.setText("");
		username.setColumns(10);
		username.setBounds(178, 80, 224, 20);
		panel.add(username);
		
		password = new JTextField();
		password.setText("");
		password.setColumns(10);
		password.setBounds(178, 117, 224, 20);
		panel.add(password);
		
		JButton button = new JButton("Log In");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				login();
			}
		});
		button.setBounds(178, 183, 79, 23);
		panel.add(button);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getRootFrame().showPanel(new PublicMessagesPanel());
			}
		});
		btnCancel.setBounds(267, 183, 77, 23);
		panel.add(btnCancel);
		
		JButton button_2 = new JButton("Sign Up");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getRootFrame().showPanel(new SignUpPanel());
			}
		});
		button_2.setBounds(460, 11, 89, 23);
		panel.add(button_2);
		
		message = new JLabel("");
		message.setBounds(178, 156, 224, 14);
		panel.add(message);
		
		JLabel lblPleaseEnterYour = new JLabel("Please enter your username and password:");
		lblPleaseEnterYour.setBounds(53, 44, 329, 14);
		panel.add(lblPleaseEnterYour);
	}
	protected void login() {
		
		try {
			User user = DBLayer.getUser(username.getText(), password.getText());
			if (user == null) {
				this.message.setText("Incorrect username or password");
			}
			else {
				Session session = getRootFrame().getSession();
				session.setUser(user);
				UserHomePanel panel = new UserHomePanel();
				getRootFrame().showPanel(panel);
				panel.init();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			this.message.setText(e.getMessage());
		}
	}
}
