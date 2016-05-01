package com.github.mrebhan.imgdatacreator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Tools {

	public static String convertImage(File in, boolean hor, boolean ver, boolean random) {
		BufferedImage img;
		try {
			img = ImageIO.read(in);
			ArrayList<String> data = new ArrayList<>();
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
								data.add("[" + y + ", " + lX + ", " + y + ", " + mX + "],\n");
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
								data.add("[" + lY + ", " + x + ", " + mY + ", " + x + "],\n");
							}
							lY = -1;
							mY = -1;
						}
					}
					lY = -1;
					mY = -1;
				}
			}
			String dataStr = "";
			if (random) {
				Random r = new Random();
				while (data.size() > 1) {
					int index = r.nextInt(data.size() - 1);
					dataStr += data.get(index);
					data.remove(index);
				}
				dataStr += data.get(0);
			} else {
				for (String string : data) {
					dataStr += string;
				}
			}
			return dataStr;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

}
