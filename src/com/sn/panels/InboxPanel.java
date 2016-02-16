package com.sn.panels;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sn.dblayer.DBLayer;
import com.sn.vo.Message;

import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InboxPanel extends SNJPanel {
	private JTable table;
	private String[] columnNames = {"User",
            "Message",
            "Time"};
	
	public InboxPanel() {
		setSize(860, 600);
		setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 840, 548);
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
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				init();
			}
		});
		btnRefresh.setBounds(726, 7, 124, 23);
		add(btnRefresh);
	}
	
	public void init() {
		try {
			displayMessages(DBLayer.getInboxMessages(getRootFrame().getSession().getUser().getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void displayMessages(List<Message> messages) {
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
