package com.cb.passwdbox.type;

public class TypeBeen {
	private final static String TAG = "[passwd]TypeBeen";
	
	private String name;
	private String id;
	private String descriptor;
	
	public TypeBeen(String id, String name, String descriptor) {
		this.name = name;
		this.id = id;
		this.descriptor = descriptor;
	}

	public String getName(){
		return name;
	}
	public String getID(){
		return id;
	}
	public void setName(String name){ this.name = name; }
	public String getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
}
