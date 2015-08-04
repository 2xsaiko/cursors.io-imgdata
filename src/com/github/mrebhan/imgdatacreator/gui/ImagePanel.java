package com.github.mrebhan.imgdatacreator.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 2633354372376465519L;

	private FileSelector imageSelector;
	
	public ImagePanel() {
		super(true);
		this.imageSelector = new FileSelector(new File("").toPath(), "Image:");
		this.init();
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		
		this.add(this.imageSelector, BorderLayout.NORTH);
	}
	
	public Path getPath() {
		return imageSelector.getPath();
	}
	
}
