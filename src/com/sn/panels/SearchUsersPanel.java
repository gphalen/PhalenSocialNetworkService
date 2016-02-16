package com.sn.panels;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sn.dblayer.DBLayer;
import com.sn.utilities.ButtonColumn;
import com.sn.vo.Subscription;
import com.sn.vo.User;

public class SearchUsersPanel extends SNJPanel {
	private JTextField name;
	private JLabel message;
	private List<User> users = new ArrayList<User>();
	
	private String[] columnNames = {"Name",
            "Address",
            "Age",
            "Phone",
            "Email",
            "Subscribe"};
	private JTable table;

	/**
	 * Create the panel.
	 */
	public SearchUsersPanel() {
		setLayout(null);
		setSize(860, 600);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 860, 36);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblSearchUser = new JLabel("Search User:");
		lblSearchUser.setBounds(10, 11, 95, 14);
		panel.add(lblSearchUser);
		
		name = new JTextField();
		name.setBounds(115, 8, 451, 20);
		panel.add(name);
		name.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				searchUsers();
			}
		});
		btnSearch.setBounds(576, 7, 127, 23);
		panel.add(btnSearch);
		
		message = new JLabel("");
		message.setBounds(713, 11, 137, 14);
		panel.add(message);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 36, 850, 553);
		add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 850, 553);
		panel_1.add(scrollPane);
		
		 DefaultTableModel model = new DefaultTableModel();
		 for (String s: columnNames) {
			 model.addColumn(s);
		 }
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		//table.setEnabled(false);
	}

	protected void searchUsers() {
		try {
			users = DBLayer.searchUsers(name.getText());
			System.out.println("Total search results = " + users.size());
			displayUsersList(users);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void displayUsersList(List<User> users) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for (User u : users) {
			
			String btnLbl = null;
			
			boolean alreadySubscribed = false;
			
			for (Subscription s : getRootFrame().getSession().getUser().getSubscriptions()) {
				if (s.getSubscribedId() == u.getId()) {
					alreadySubscribed = true;
					break;
				}
			}
			
			if (!alreadySubscribed) {
				btnLbl = "Subscribe";
			}else {
				btnLbl = "-";
			}
			
			model.addRow(new Object[]{u.getName(), u.getAddress(), u.getAge(), u.getPhone(), u.getEmail(), btnLbl});
		}
		
		Action subscribe = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					int rowIndex = Integer.valueOf(e.getActionCommand());
					
					System.out.println("Row number = " + rowIndex);
					int subscribed_id = users.get(rowIndex).getId();
					boolean alreadySubscribed = false;
					
					for (Subscription s : getRootFrame().getSession().getUser().getSubscriptions()) {
						if (s.getSubscribedId() == subscribed_id) {
							alreadySubscribed = true;
							break;
						}
					}
					
					if (!alreadySubscribed) {
						DBLayer.subscribe(getRootFrame().getSession().getUser().getId(), subscribed_id);
						message.setText("Successfully subscribed");
					}		
				} catch (Exception e1) {
					e1.printStackTrace();
					message.setText(e1.getMessage());
				}
			}
		};
		ButtonColumn buttonColumn = new ButtonColumn(table, subscribe, 5);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
	}
}