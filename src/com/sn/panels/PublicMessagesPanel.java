package com.sn.panels;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sn.dblayer.DBLayer;
import com.sn.vo.Message;
import java.awt.Color;

public class PublicMessagesPanel extends SNJPanel {
	private JTable table;
	
	private String[] columnNames = {"User",
            "Message",
            "Time"};
	
	public PublicMessagesPanel() {
		setSize(880, 600);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		JLabel lblPublicPosts = new JLabel("Public Messages:");
		lblPublicPosts.setBounds(10, 20, 200, 14);
		add(lblPublicPosts);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 860, 549);
		add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		for (String s : columnNames) {
			model.addColumn(s);
		}
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getRootFrame().showPanel(new LogInPanel());
			}
		});
		btnLogIn.setBounds(672, 11, 89, 23);
		add(btnLogIn);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getRootFrame().showPanel(new SignUpPanel());
			}
		});
		btnSignUp.setBounds(781, 11, 89, 23);
		add(btnSignUp);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				init();
			}
		});
		btnRefresh.setBounds(317, 11, 107, 23);
		add(btnRefresh);
		init();
	}
	
	private void init() {
		try {
			displayAllPublicPosts(DBLayer.getAllPublicMessages());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void displayAllPublicPosts(List<Message> messages) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for (Message m : messages) {
			model.addRow(new Object[]{m.getSender().getName(), m.getText(), m.getSendingTime()});
		}
		
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
	} 
}
