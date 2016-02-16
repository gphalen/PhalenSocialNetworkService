package com.sn.panels;
import javax.swing.ButtonGroup;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import com.sn.dblayer.DBLayer;
import com.sn.vo.Message;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class SendMessagePanel extends SNJPanel {
	
	private JRadioButton rdbtnForPublic;
	private JRadioButton rdbtnForSubscribersOnly;
	private JLabel message;
	private JTextPane text;
	
	public SendMessagePanel() {
		setLayout(null);
		setSize(860, 600);
		text = new JTextPane();
		text.setBounds(146, 86, 574, 116);
		add(text);
		
		JLabel lblEnterMessage = new JLabel("Enter Message:");
		lblEnterMessage.setBounds(146, 61, 139, 14);
		add(lblEnterMessage);
		
		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sendMessage();
			}
		});
		btnSend.setBounds(631, 286, 89, 23);
		add(btnSend);
		
		rdbtnForPublic = new JRadioButton("For Public");
		rdbtnForPublic.setSelected(true);
		rdbtnForPublic.setBounds(489, 220, 89, 23);
		add(rdbtnForPublic);
		
		rdbtnForSubscribersOnly = new JRadioButton("For Subscribers Only");
		rdbtnForSubscribersOnly.setBounds(586, 220, 150, 23);
		add(rdbtnForSubscribersOnly);
		
		ButtonGroup grp = new ButtonGroup();
		grp.add(rdbtnForPublic);
		grp.add(rdbtnForSubscribersOnly);
		
		message = new JLabel("");
		message.setBounds(530, 261, 190, 14);
		add(message);
	}

	protected void sendMessage() {
		
		boolean forPublic = true;
		
		if (rdbtnForSubscribersOnly.isSelected()) {
			forPublic = false;
		}
		
		Message msg = new Message();
		msg.setSender(getRootFrame().getSession().getUser());
		msg.setReceiverType(forPublic ? 1 : 2);
		msg.setSendingTime(new Date());
		
		String str = text.getText().trim();
		msg.setText(str);
		
		if (str.length() <= 0 || str.length() > 130) {
			message.setText("Must length must be 1-130");
			return;
		}
		
		try {
			DBLayer.sendMessage(msg);
			message.setText("Successfully sent.");
			text.setText("");
		} catch (Exception e) {
			e.printStackTrace();
			message.setText(e.getMessage());
		}
		
	}
}
