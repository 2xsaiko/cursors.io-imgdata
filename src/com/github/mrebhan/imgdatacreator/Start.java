package com.github.mrebhan.imgdatacreator;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

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
		opt.addOption("1", "img1", true, "Input file 1 (PNG/other supported formats)");
		opt.addOption("2", "img2", true, "Input file 2 (PNG/other supported formats)");
		opt.addOption("3", "img3", true, "Input file 3 (PNG/other supported formats)");
		opt.addOption("4", "img4", true, "Input file 4 (PNG/other supported formats)");
		opt.addOption("5", "img5", true, "Input file 5 (PNG/other supported formats)");
		opt.addOption("6", "img6", true, "Input file 6 (PNG/other supported formats)");
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
		if (!line.hasOption('o')) {
			System.err.println("Needs -o (Output file)");
			System.exit(-1);
		}


		if (line.hasOption('h'))
			hor = true;
		if (line.hasOption('v'))
			ver = true;
		out = new File(line.getOptionValue('o'));

		String filecontents = "";

		try {
			InputStream str = Start.class.getClassLoader().getResourceAsStream("cursor_jesus.txt");
			BufferedReader r = new BufferedReader(new InputStreamReader(str));
			String buf = "";
			while ((buf = r.readLine()) != null) {
				filecontents += buf + '\n';
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i = 1; i < 7; i++) {
			String imgStr = "<IMG_" + "ABCDEF".charAt(i - 1) + ">";
			if (line.hasOption(Integer.toString(i).charAt(0))) {
				in = new File(line.getOptionValue(Integer.toString(i).charAt(0)));
				System.out.println("Processing image " + i + "...");
				if (!in.isFile()) {
					System.err.println("Input file doesn't exist");
				}

				String dataStr = convertImage(in, hor, ver);
				filecontents = filecontents.replace(imgStr, dataStr);
			} else {
				System.out.println("Skipping image " + i + ".");
				filecontents = filecontents.replace(imgStr, "[0, 0, 0, 0]");
			}
		}

		try {
			if (out.exists())
				out.delete();
			Files.write(out.toPath(), filecontents.getBytes(), StandardOpenOption.CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String convertImage(File in, boolean hor, boolean ver) {
		BufferedImage img;
		try {
			img = ImageIO.read(in);
			String dataStr = "";
			if (hor) {
				int lX = -1;
				int mX = -1;
				for (int y = 0; y < img.getHeight(); y++) {
					for (int x = 0; x < img.getWidth(); x++) {
						float r = img.getColorModel().getRed(img.getRaster().getDataElements(x, y, null));
						float g = img.getColorModel().getGreen(img.getRaster().getDataElements(x, y, null));
						float b = img.getColorModel().getBlue(img.getRaster().getDataElements(x, y, null));

						float comp = r + g + b / 3f;
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
						float r = img.getColorModel().getRed(img.getRaster().getDataElements(x, y, null));
						float g = img.getColorModel().getGreen(img.getRaster().getDataElements(x, y, null));
						float b = img.getColorModel().getBlue(img.getRaster().getDataElements(x, y, null));

						float comp = r + g + b / 3f;
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
			return dataStr;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

}
