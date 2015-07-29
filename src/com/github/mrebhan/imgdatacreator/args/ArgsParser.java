package com.github.mrebhan.imgdatacreator.args;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArgsParser {
	
	private String[] args;
	
	private List<ArgumentSpecification> allArgs;
	private List<ArgumentSpecification> collectedArgs;
	private List<ArgumentSpecification> redundantArgs;
	private boolean state;
	
	public ArgsParser(String[] args) {
		this.args = args;
		this.state = false;
		this.allArgs = new ArrayList<ArgumentSpecification>();
		this.collectedArgs = new ArrayList<ArgumentSpecification>();
		this.redundantArgs = new ArrayList<ArgumentSpecification>();
	}
	
	public ArgsParser addArgument(ArgumentSpecification argSpec) {
		this.allArgs.add(argSpec);
		return this;
	}
	
	public void reset() {
		collectedArgs.clear();
		redundantArgs.clear();
		state = false;
	}
	
	public void run() {
		if (state == true)
			throw new IllegalStateException("ArgsParser has already run! Please call reset() to be able to run this again.");
		state = true;
		
		List<String> errors = new ArrayList<String>();
		
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			
			if (arg.startsWith("-")) {
				char[] cArray = arg.toCharArray();
				cArray = Arrays.copyOfRange(cArray, 1, cArray.length);
				int len = cArray.length;
				for (int j = 0; j < cArray.length; j++) {
					char c = cArray[j];
					boolean defined = false;
					boolean hasExtended = true;
					for (ArgumentSpecification spec : allArgs) {
						if (spec.getName() == c) {
							defined = true;
							collectedArgs.add(spec);
							if (spec.getExtended()) {
								if (j == len-1) {
									
								} else {
									errors.add("ERROR: argument " + c + " is extended, but has no text");
								}
							}
							break;
						}
					}
					if (!defined) {
						errors.add("ERROR: undefined argument " + c + "!");
					} else {
						
					}
				}
			}
		}
	}

}
