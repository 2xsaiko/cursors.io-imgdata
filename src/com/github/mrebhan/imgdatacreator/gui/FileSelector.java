package com.github.mrebhan.imgdatacreator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileSelector extends JPanel {
	
	private Runnable fileChosenRunnable;
	private JButton chooseButton;
	private JTextField fileField;
	private Path currentPath;
	private JFileChooser fileChooser;
	private JLabel label;
	
	public FileSelector() {
		this(new File(".").toPath());
	}
	
	public FileSelector(String s) {
		this(new File(".").toPath(), s);
	}
	
	public FileSelector(Path p) {
		this(p, "");
	}
	
	public FileSelector(Path p, String text) {
		this.setLayout(new BorderLayout(2, 2));
		this.currentPath = p;
		this.fileChooser = new JFileChooser(this.currentPath.toFile());
		this.fileChosenRunnable = new Runnable() {
			
			@Override
			public void run() {
				// stub
			}
		};
		
		this.chooseButton = new JButton(new AbstractAction("...") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileSelector.this.fileChooser.setSelectedFile(FileSelector.this.currentPath.toFile());
				if (FileSelector.this.fileChooser.showDialog(FileSelector.this, "Choose...") == JFileChooser.APPROVE_OPTION) {
					FileSelector.this.currentPath = fileChooser.getSelectedFile().toPath();
					FileSelector.this.updateText();
				}
			}
		});
		
		this.fileField = new JTextField();
		this.fileField.setEditable(false);
		
		if (!text.isEmpty()) {
			this.add(this.label = new JLabel(text), BorderLayout.WEST);
		}
		this.add(this.fileField, BorderLayout.CENTER);
		this.add(this.chooseButton, BorderLayout.EAST);
		
		this.updateText();
	}
	
	private void updateText() {
		this.fileField.setText(this.getPath().toAbsolutePath().toString());
	}
	
	public Path getPath() {
		return this.currentPath;
	}
	
}
