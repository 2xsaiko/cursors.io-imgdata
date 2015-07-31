package com.github.mrebhan.imgdatacreator.gui;

import java.awt.Container;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.github.mrebhan.imgdatacreator.Start;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 4375807772690416573L;

	private JTabbedPane tabPane;
	
	private MainPanel mainPanel;
	
	public MainFrame() {
		super("ImgData Creator [beta]");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		init();
		this.setVisible(true);
	}
	
	private void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			Image _16px = ImageIO.read(Start.class.getResourceAsStream("/16px.png"));
			this.setIconImage(_16px);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Container contentPane = this.getContentPane();
		
		mainPanel = new MainPanel(this);
		
		tabPane = new JTabbedPane();
		tabPane.add("Main", mainPanel);
//		tabPane.add("Image 1", new JPanel());
//		tabPane.add("Image 2", new JPanel());
		
		
		contentPane.add(tabPane);
		
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
}
