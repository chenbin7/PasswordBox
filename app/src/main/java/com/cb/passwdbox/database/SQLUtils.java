package com.cb.passwdbox.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cb.passwdbox.property.Const;

public class SQLUtils {
	
	public static SQLiteDatabase getDataBase(Context context){
		DataBaseHelper helper = new DataBaseHelper(context, Const.DATABASE_NAME);
		return helper.getWritableDatabase();
	}
	
	

}
