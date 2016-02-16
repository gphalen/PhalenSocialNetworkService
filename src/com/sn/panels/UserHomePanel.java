package com.sn.panels;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class UserHomePanel extends SNJPanel {

	private InboxPanel panel;
	private EditProfilePanel editProfilePanel;
	
	/**
	 * Create the panel.
	 */
	public UserHomePanel() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		setSize(880, 600);
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 38, 860, 539);
		add(tabbedPane_1);
		
		panel = new InboxPanel();
		tabbedPane_1.addTab("Inbox", null, panel, null);
		
		SendMessagePanel panel_1 = new SendMessagePanel();
		tabbedPane_1.addTab("Send Message", null, panel_1, null);
		
		JPanel searchUsersPanel = new SearchUsersPanel();
		searchUsersPanel.setSize(600, 400);
		tabbedPane_1.addTab("Search Users", null, searchUsersPanel, null);
		
		editProfilePanel = new EditProfilePanel();
		tabbedPane_1.addTab("Edit Profile", null, editProfilePanel, null);
		
		JButton btnNewButton = new JButton("Log Out");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getRootFrame().showPanel(new LogInPanel());
			}
		});
		btnNewButton.setBounds(781, 11, 89, 23);
		add(btnNewButton);
	
		
	}
	
	public void init() {
		panel.init();
		editProfilePanel.init();
	}
}
