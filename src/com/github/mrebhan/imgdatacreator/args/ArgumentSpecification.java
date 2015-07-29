package com.github.mrebhan.imgdatacreator.args;

public class ArgumentSpecification {
	
	private char name;
	private boolean extended;
	private boolean required;
	private String extendedArgContents;
	
	public ArgumentSpecification(char name, boolean extended, boolean required) {
		this.name = name;
		this.extended = extended;
		this.required = required;
		this.extendedArgContents = null;
	}
	
	public char getName() {
		return name;
	}
	
	public boolean getExtended() {
		return extended;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	public void setExtendedArgumentContents(String eac) {
		if (!extended)
			throw new IllegalStateException("Argument -" + name + ": Can not set extended text on non-extended argument!");
		this.extendedArgContents = eac;
	}
	
	public String getExtendedArgumentContents() {
		return this.extendedArgContents;
	}
	
}
