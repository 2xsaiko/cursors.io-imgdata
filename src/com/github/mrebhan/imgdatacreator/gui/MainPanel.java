package com.github.mrebhan.imgdatacreator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = -856196959232976039L;

	private MainFrame mainFrame;
	private JButton startBtn;
	private JProgressBar progressBar;
	private FileSelector fileSelector;
	
	public MainPanel(MainFrame mainFrame) {
		super(true);
		this.mainFrame = mainFrame;
		this.startBtn = new JButton(new AbstractAction("Go!") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickStart();
			}
		});
		this.progressBar = new JProgressBar(0, 6);
		this.fileSelector = new FileSelector("Output:");
		init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		
		this.add(startBtn, BorderLayout.SOUTH);
		this.add(fileSelector, BorderLayout.NORTH);
	}
	
	private void onClickStart() {
		
	}
	
	private void doneWorking() {
		
	}

}
