package com.cb.passwdbox.been;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cb.passwdbox.property.Const;
import com.cb.passwdbox.utils.Utils;

public class PasswdBeen {
	private final static String TAG = "[passwd]PasswdBeen";
	
	private String id;
	private String name;
	private String passwd;
	private String type;

	
	protected PasswdBeen(String id,String name, String passwd, String type){
		this.id = id;
		this.name = name;
		this.passwd = passwd;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPasswd() {
		return passwd;
	}

	public String getType() {
		return type;
	}




	






	

	


	


}
