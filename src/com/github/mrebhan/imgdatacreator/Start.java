package com.github.mrebhan.imgdatacreator;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@SuppressWarnings("deprecation")
public class Start {

	public static void main(String[] args) {
		Options opt = new Options();
		opt.addOption("h", "Horizontal tiling");
		opt.addOption("v", "Vertical tiling");
		opt.addOption("i", "in", true, "Input file (PNG/other supported formats)");
		opt.addOption("o", "out", true, "Output file (Txt file)");
		CommandLine line = null;
		try {
			line = new GnuParser().parse(opt, args);
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		boolean hor = false;
		boolean ver = false;
		File in = null;
		File out = null;

		if (!line.hasOption('h') && !line.hasOption('v')) {
			System.err.println("Needs at least one of -h and -v");
			System.exit(-1);
		}
		if (!line.hasOption('i')) {
			System.err.println("Needs -i (Input file)");
			System.exit(-1);
		}
		if (!line.hasOption('o')) {
			System.err.println("Needs -o (Output file)");
			System.exit(-1);
		}


		if (line.hasOption('h'))
			hor = true;
		if (line.hasOption('v'))
			ver = true;
		in = new File(line.getOptionValue('i'));
		out = new File(line.getOptionValue('o'));

		if (!in.isFile()) {
			System.err.println("Input file doesn't exist");
			System.exit(-1);
		}

		try {
			BufferedImage img = ImageIO.read(in);
			ArrayList<String> data = new ArrayList<String>();
			String dataStr = "";
			if (hor) {
				int lX = -1;
				int mX = -1;
				for (int y = 0; y < img.getHeight(); y++) {
					for (int x = 0; x < img.getWidth(); x++) {
						int r = img.getColorModel().getRed(img.getRaster().getDataElements(x, y, null));
						int g = img.getColorModel().getGreen(img.getRaster().getDataElements(x, y, null));
						int b = img.getColorModel().getBlue(img.getRaster().getDataElements(x, y, null));

						int comp = r+g+b/3;
						int bn = 128;

						if (comp < bn) {
							if (lX == -1)
								lX = x;
							mX = x;
						} else {
							if (lX != -1 && mX != -1) {
								dataStr += "[" + y + ", " + lX + ", " + y + ", " + mX + "],\n";
							}
							lX = -1;
							mX = -1;
						}
					}
					lX = -1;
					mX = -1;
				}
			}
			if (ver) {
				int lY = -1;
				int mY = -1;
				for (int x = 0; x < img.getWidth(); x++) {
					for (int y = 0; y < img.getHeight(); y++) {
						int r = img.getColorModel().getRed(img.getRaster().getDataElements(x, y, null));
						int g = img.getColorModel().getGreen(img.getRaster().getDataElements(x, y, null));
						int b = img.getColorModel().getBlue(img.getRaster().getDataElements(x, y, null));

						int comp = r+g+b/3;
						int bn = 128;

						if (comp < bn) {
							if (lY == -1)
								lY = y;
							mY = y;
						} else {
							if (lY != -1 && mY != -1) {
								dataStr += "[" + lY + ", " + x + ", " + mY + ", " + x + "],\n";
							}
							lY = -1;
							mY = -1;
						}
					}
					lY = -1;
					mY = -1;
				}
			}
			InputStream str = Start.class.getClassLoader().getResourceAsStream("cursor_jesus.txt");
		    BufferedReader r = new BufferedReader(new InputStreamReader(str));
		    String s = "";
		    String buf = "";
		    while ((buf = r.readLine()) != null) {
		    	s += buf + '\n';
		    }
		    s = s.replace("<REPLACE_HERE>", dataStr);
		    if (out.exists())
		    	out.delete();
		    Files.write(out.toPath(), s.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
