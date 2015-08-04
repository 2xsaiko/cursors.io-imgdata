package com.github.mrebhan.imgdatacreator.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = -856196959232976039L;

	private MainFrame mainFrame;
	private JButton startBtn;
	private JProgressBar progressBar;
	private FileSelector fileSelector;
	private JCheckBox vertical;
	private JCheckBox horizontal;
	private JPanel middlePanel;
	
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
		this.vertical = new JCheckBox("Vertical lines");
		this.horizontal = new JCheckBox("Horizontal lines");
		this.middlePanel = new JPanel();
		init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		
		this.add(startBtn, BorderLayout.SOUTH);
		this.add(fileSelector, BorderLayout.NORTH);
		
		this.middlePanel.add(this.horizontal);
//		this.horizontal.setLocation(1, 1);
		this.middlePanel.add(this.vertical);
//		this.vertical.setLocation(1, 21);
		this.add(this.middlePanel, BorderLayout.CENTER);
	}
	
	private void onClickStart() {
		this.remove(this.startBtn);
		this.add(this.progressBar, BorderLayout.SOUTH);
		this.mainFrame.begin();
	}
	
	public void doneWorking() {
		this.setEnabled(true);
		this.remove(this.progressBar);
		this.add(this.startBtn, BorderLayout.SOUTH);
	}
	
	public void setProgress(int i) {
		this.progressBar.setValue(i);
	}
	
	public boolean horizontal() {
		return horizontal.isSelected();
	}
	
	public boolean vertical() {
		return vertical.isSelected();
	}
	
	public Path getOutputFile() {
		return fileSelector.getPath();
	}
	
	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
		for (Component component : getComponents()) {
			component.setEnabled(enable);
			if (component instanceof Container) {
				Container c = (Container) component;
				for (Component component2 : c.getComponents()) {
					component2.setEnabled(enable);
				}
			}
		}
	}

}
