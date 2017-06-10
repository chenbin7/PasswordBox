package com.cb.passwdbox.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.cb.passwdbox.property.Const;

public class DataBaseHelper extends SQLiteOpenHelper {
	private final static String TAG = "[PasswdBox]BaseHelper";

	static DatabaseErrorHandler errorHandler = new DatabaseErrorHandler() {

		@Override
		public void onCorruption(SQLiteDatabase arg0) {
			Log.d(TAG, "DatabaseErrorHandler onCorruption...");

		}
	};

	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		this(context, name, factory, version, null);
		// TODO Auto-generated constructor stub
	}

	public DataBaseHelper(Context context, String name, CursorFactory factory) {
		this(context, name, factory, 1, null);
		// TODO Auto-generated constructor stub
	}

	public DataBaseHelper(Context context, String name) {
		this(context, name, null, 1, null);
		// TODO Auto-generated constructor stub
	}
	
	private void createTablePaasswd(SQLiteDatabase db){
		Log.d(TAG, "DataBaseHelper   onCreate...");
		StringBuffer sb = new StringBuffer("create table ");
		sb.append(Const.TABLE_PASSWD_NAME).append("(");
		sb.append("id char(36) primary key, ");
		sb.append("name varchar(256), ");
		sb.append("passwd varchar(256), ");
		sb.append("type char(36) ");
		sb.append(")");
		String sql = sb.toString();
		Log.d(TAG, "create sql = " + sql);
		try {
			db.execSQL(sql);
			Log.d(TAG, "create table passwd succ...");
		} catch (Exception e) {
			Log.d(TAG, "create table passwd fail...");
			e.printStackTrace();
		}
		
	}
	private void createTableType(SQLiteDatabase db){
		Log.d(TAG, "DataBaseHelper   onCreate...");
		StringBuffer sb = new StringBuffer("create table ");
		sb.append(Const.TABLE_TYPE_NAME).append("(");
		sb.append("id char(36) primary key, ");
		sb.append("name varchar(256) ");
		sb.append(")");
		String sql = sb.toString();
		Log.d(TAG, "create sql = " + sql);
		try {
			db.execSQL(sql);
			Log.d(TAG, "create table type succ...");
		} catch (Exception e) {
			Log.d(TAG, "create table type fail...");
			e.printStackTrace();
		}
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTableType(db);
		createTablePaasswd(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		Log.d(TAG, "   onUpgrade...");
		Log.d(TAG, db.getPath() + "  " + arg1 + "   " + arg2);

	}
}
