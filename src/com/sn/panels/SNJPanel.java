package com.sn.panels;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sn.frames.MainFrame;

public class SNJPanel extends JPanel {
	
	public MainFrame getRootFrame() {
		MainFrame frame = (MainFrame) SwingUtilities.getRoot(this);
		return frame;
	}
}
