package com.sn.panels;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.sn.dblayer.DBLayer;
import com.sn.vo.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditProfilePanel extends SNJPanel {
	private JTextField email;
	private JTextField username;
	private JTextField address;
	private JTextField age;
	private JTextField phone;
	private JTextField name;
	private JLabel message;
	
	public EditProfilePanel() {
		setLayout(null);
		setSize(860, 600);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(233, 78, 491, 279);
		add(panel);
		
		JLabel label = new JLabel("Username:");
		label.setBounds(62, 63, 82, 14);
		panel.add(label);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(62, 39, 82, 14);
		panel.add(lblEmail);
		
		JLabel label_2 = new JLabel("Address:");
		label_2.setBounds(62, 88, 82, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Age:");
		label_3.setBounds(62, 113, 82, 14);
		panel.add(label_3);
		
		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(62, 138, 82, 14);
		panel.add(lblPhone);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(150, 36, 250, 20);
		panel.add(email);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(150, 63, 250, 20);
		panel.add(username);
		
		address = new JTextField();
		address.setColumns(10);
		address.setBounds(150, 88, 250, 20);
		panel.add(address);
		
		age = new JTextField();
		age.setColumns(10);
		age.setBounds(150, 113, 250, 20);
		panel.add(age);
		
		phone = new JTextField();
		phone.setColumns(10);
		phone.setBounds(150, 138, 250, 20);
		panel.add(phone);
		
		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				save();
			}
		});
		btnSave.setBounds(150, 200, 89, 23);
		panel.add(btnSave);
		
		JButton button_1 = new JButton("Cancel");
		button_1.setBounds(249, 200, 89, 23);
		panel.add(button_1);
		
		JLabel label_5 = new JLabel("Name:");
		label_5.setBounds(62, 14, 82, 14);
		panel.add(label_5);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(150, 11, 250, 20);
		panel.add(name);
		
		message = new JLabel("");
		message.setBounds(150, 169, 250, 14);
		panel.add(message);
	}
	
	protected void save() {
		
		User updatedUser = new User();
		updatedUser.setAddress(address.getText());
		updatedUser.setAge(Integer.parseInt(age.getText()));
		updatedUser.setEmail(email.getText());
		updatedUser.setId(getRootFrame().getSession().getUser().getId());
		updatedUser.setName(name.getText());
		updatedUser.setPhone(phone.getText());
		updatedUser.setUsername(username.getText());
		
		try {
			DBLayer.updateUser(updatedUser);
			getRootFrame().getSession().setUser(updatedUser);
			message.setText("Successfully updated.");
		} catch (Exception e) {
			e.printStackTrace();
			message.setText(e.getMessage());
		}
	}

	public void init() {
		User user = getRootFrame().getSession().getUser();
		name.setText(user.getName());
		email.setText(user.getEmail());
		username.setText(user.getUsername());
		address.setText(user.getAddress());
		age.setText(user.getAge() + "");
		phone.setText(user.getPhone());
	}	
}
