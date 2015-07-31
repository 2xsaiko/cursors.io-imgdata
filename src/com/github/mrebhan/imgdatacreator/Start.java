package com.github.mrebhan.imgdatacreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.github.mrebhan.imgdatacreator.gui.MainFrame;

@SuppressWarnings("deprecation")
public class Start {

	public static void main(String[] args) {
		if (args.length == 0) {
			//GUI
			MainFrame mainFrame = new MainFrame();
		} else {
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
				InputStream str = Start.class.getClassLoader().getResourceAsStream("/cursor_jesus.txt");
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

					String dataStr = Tools.convertImage(in, hor, ver);
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

	}

}
