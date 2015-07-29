package com.github.mrebhan.imgdatacreator;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
		
		
		
		
	}

}
