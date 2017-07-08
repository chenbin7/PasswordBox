package com.cb.passwdbox.database;


import com.cb.passwdbox.property.Const;
import com.cb.passwdbox.utils.Utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SPUtils {

	private final static String TAG = "SPUtils";
	private SharedPreferences share = null;
	
	public SPUtils(Context context){
		this.share = context.getSharedPreferences(Const.SHARE_PREFERENCE_NAME, 0);
	}

	public int getLoginMode(){
		int mode = share.getInt(Const.LOGIN_MODE_KEY,Const.LOGIN_MODE_CLEAR);
		return mode;
	}

	public boolean saveLoginMode(int mode){
		if(mode != Const.LOGIN_MODE_CLEAR && mode != Const.LOGIN_MODE_GRABLED )
			return false;
		Editor editor = share.edit();
		editor.putInt(Const.LOGIN_MODE_KEY,mode);
		return editor.commit();
	}

	public boolean isFirst(){
		String savePasswd = share.getString(Const.SPN_LOGIN_PASSWD_KEY, null);
		return  (savePasswd == null)||(savePasswd.trim().equals(""));
	}
	
	public boolean savePasswd(String passwd){
		Editor editor = share.edit();
		String save = Utils.getMD5(passwd);
		Log.d(TAG, "savePasswd = "+save);
		if(save == null)
			return false;
		editor.putString(Const.SPN_LOGIN_PASSWD_KEY, save);
		return editor.commit();
	}
	
	public boolean saveLoginTryMax(int max){
		Editor editor = share.edit();
		editor.putInt(Const.SPN_LOGIN_TRY_MAX, max);
		return editor.commit();
	}
	
	public boolean saveLoginWrongCount(int count){
		Editor editor = share.edit();
		editor.putInt(Const.SPN_LOGIN_WRONG_COUNT_KEY, count);
		return editor.commit();
	}
	
	public boolean isPasswdRight(String passwd){
		String temp = Utils.getMD5(passwd);
		if(temp == null)
			return false;
		String savePasswd = share.getString(Const.SPN_LOGIN_PASSWD_KEY, null);
		return temp.equals(savePasswd);
	}
	
	public int getLoginTryMaxCount(){
		return share.getInt(Const.SPN_LOGIN_TRY_MAX, Const.SPN_LOGIN_TRY_MAX_DEFAULT);
	}
	
	public int getLoginWrongCount(){
		return share.getInt(Const.SPN_LOGIN_WRONG_COUNT_KEY, 0);
	}
	
	
	
}
