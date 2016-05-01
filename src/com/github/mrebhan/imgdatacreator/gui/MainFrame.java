package com.github.mrebhan.imgdatacreator.gui;

import java.awt.Container;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.github.mrebhan.imgdatacreator.Start;
import com.github.mrebhan.imgdatacreator.Tools;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 4375807772690416573L;

	private JTabbedPane tabPane;
	
	private MainPanel mainPanel;
	private ImagePanel imgP1;
	private ImagePanel imgP2;
	private ImagePanel imgP3;
	private ImagePanel imgP4;
	private ImagePanel imgP5;
	private ImagePanel imgP6;
	
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
		imgP1 = new ImagePanel();
		imgP2 = new ImagePanel();
		imgP3 = new ImagePanel();
		imgP4 = new ImagePanel();
		imgP5 = new ImagePanel();
		imgP6 = new ImagePanel();
		
		tabPane = new JTabbedPane();
		tabPane.add("Main", mainPanel);
		tabPane.add("Image 1", imgP1);
		tabPane.add("Image 2", imgP2);
		tabPane.add("Image 3", imgP3);
		tabPane.add("Image 4", imgP4);
		tabPane.add("Image 5", imgP5);
		tabPane.add("Image 6", imgP6);
		
		contentPane.add(tabPane);
		
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public void begin() {
		this.tabPane.setEnabled(false);
		this.mainPanel.setEnabled(false);
		boolean horizontal = mainPanel.horizontal();
		boolean vertical = mainPanel.vertical();
		boolean random = mainPanel.random();
		
		if (!(horizontal || vertical)) {
			this.showDialog("Error", "At least one of:\n   ·Horizontal\n   ·Vertical\n is required.", JOptionPane.ERROR_MESSAGE);
			this.tabPane.setEnabled(true);
			this.mainPanel.doneWorking();
			return;
		}
		
		Path output = mainPanel.getOutputFile();
		
		if (output.toFile().isDirectory()) {
			this.showDialog("Error", "Output can not be an existing directory!", JOptionPane.ERROR_MESSAGE);
			this.tabPane.setEnabled(true);
			this.mainPanel.doneWorking();
			return;
		}
		
		if (output.toFile().isFile()) {
			boolean continueB = this.showYesNoDialog("Conflict", "Output file already exists. Overwrite?");
			if (!continueB) {
				this.tabPane.setEnabled(true);
				this.mainPanel.doneWorking();
				return;
			}
			output.toFile().delete();
		}
		
		String errors = "";
		
		Path[] images = new Path[6];
		images[0] = imgP1.getPath();
		images[1] = imgP2.getPath();
		images[2] = imgP3.getPath();
		images[3] = imgP4.getPath();
		images[4] = imgP5.getPath();
		images[5] = imgP6.getPath();
		
		String[] imgPr = new String[] {
				"[0, 0, 0, 0]","[0, 0, 0, 0]","[0, 0, 0, 0]","[0, 0, 0, 0]","[0, 0, 0, 0]","[0, 0, 0, 0]"
		};
		
		int i = 0;
		
		for (Path path : images) {
			
			if (!path.toFile().isFile()) {
				errors += "Could not find file " + path.toAbsolutePath().toString() + ". Skipping.\n";
			} else {
				String tex = Tools.convertImage(path.toFile(), horizontal, vertical, random);
				if (tex.equals("")) {
					errors += "Error while loading file " + path.toAbsolutePath().toString() + ". Invalid or empty image.";
				} else {
					imgPr[i] = tex;
				}
			}
			i++;
			this.mainPanel.setProgress(i);
		}
		

		String filecontents = "";

		try {
			InputStream str = Start.class.getResourceAsStream("/cursor_jesus.txt");
			BufferedReader r = new BufferedReader(new InputStreamReader(str));
			String buf = "";
			while ((buf = r.readLine()) != null) {
				filecontents += buf + '\n';
			}
		} catch (Exception e) {
			errors += e.getLocalizedMessage() + "\n";
			e.printStackTrace();
		}
		
		for (int j = 0; j < imgPr.length; j++) {
			String imgStr = "<IMG_" + "ABCDEF".charAt(j) + ">";
			filecontents = filecontents.replace(imgStr, imgPr[j]);
		}
		
		try {
			Files.write(output, filecontents.getBytes(), StandardOpenOption.CREATE);
		} catch (Exception e) {
			errors += e.getLocalizedMessage() + "\n";
			e.printStackTrace();
		}
		
		this.showDialog("Done!", "The operation has completed! The following errors/warnings have been detected:\n" + errors, JOptionPane.INFORMATION_MESSAGE);
		
		this.tabPane.setEnabled(true);
		this.mainPanel.doneWorking();
		
	}
	
	private void showDialog(String title, String content, int type) {
		JOptionPane.showMessageDialog(this, content, title, type);
	}
	
	private boolean showYesNoDialog(String title, String content) {
		int value = JOptionPane.showConfirmDialog(this, content, title, JOptionPane.YES_NO_OPTION);
		return value == JOptionPane.YES_OPTION;
	}
	
}
