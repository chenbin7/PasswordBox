package com.cb.passwdbox.been;

public class TypeBeen {
	private final static String TAG = "[passwd]TypeBeen";
	
	private String name;
	private String id;
	
	protected TypeBeen(String id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName(){
		return name;
	}
	public String getID(){
		return id;
	}
	public void setName(String name){ this.name = name; }

}
