package com.sn.frames;

import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sn.panels.PublicMessagesPanel;
import com.sn.vo.Session;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private Session session = new Session();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setSize(900, 640);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		showPanel(new PublicMessagesPanel());
	}
	
	public void showPanel(JPanel panel) {
		this.getContentPane().removeAll();
		this.getContentPane().add(panel);
		
		panel.setSize(this.getWidth(), this.getHeight());
		
		this.validate();
		this.repaint();
		panel.setVisible(true);
		
	}

	public Session getSession() {
		return session;
	}
}
