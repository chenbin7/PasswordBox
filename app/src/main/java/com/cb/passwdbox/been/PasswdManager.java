package com.cb.passwdbox.been;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cb.passwdbox.database.SQLUtils;
import com.cb.passwdbox.property.Const;
import com.cb.passwdbox.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PasswdManager {

    private static PasswdManager manager;
    private static Context ctx;
    private static boolean change_flag;
    List<PasswdBeen> list;

    public static PasswdManager getInstance(Context context){
        if(manager == null){
            manager = new PasswdManager();
            ctx = context;
            change_flag = false;
        }
        return manager;
    }

    private List<PasswdBeen> getPasswds(){
        SQLiteDatabase database = SQLUtils.getDataBase(ctx);
        String[] columns = new String[]{"id","name","passwd","type"};
        Cursor cursor = database.query(Const.TABLE_PASSWD_NAME, columns, null, null, null, null, null);
        List<PasswdBeen> list = new ArrayList<PasswdBeen>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String passwd = cursor.getString(2);
            String type = cursor.getString(3);
            PasswdBeen been = new PasswdBeen(id, name, passwd, type);
            list.add(been);
        }
        return list;
    }

    private void checkList(){
        if(!change_flag && list != null)
            return;
        list = getPasswds();
    }

    private List<String> getAllPasswds(){
        checkList();
        List<String> passwds = new ArrayList<String>();
        for (int i = 0;i<list.size();i++){
            passwds.add(list.get(i).getName());
        }
        return passwds;
    }

    public int getSameCount(String pwd){
        checkList();
        int count = 0;
        ArrayList<String> arr = (ArrayList<String>) getAllPasswds();
        if(pwd == null || arr == null || arr.size() == 0)
            return 0;
        for (int i = 0;i<arr.size();i++){
            if(pwd.equals(arr.get(i)))
                ++count;
        }
        return --count; //self
    }

    public int getSimilarity(String pwd){
        if(pwd == null || pwd.trim() == "")
            return 0;
        int count = 0;
        checkList();
        for (int i = 0;i<list.size();i++){
            String temp = list.get(i).getName();
            if(Utils.compareString(pwd, temp) < 3){
                count++;
            }
        }
        --count;//self
        return count*100/list.size();
    }

    public  String getMaxSimilyOne(String pwd){
        if(pwd == null || pwd.trim() == "")
            return "";
        checkList();
        int max_count = 0;
        String same = "";
        boolean self = false;
        for (int i = 0;i<list.size();i++){
            String temp = list.get(i).getName();
            if(pwd.equals(temp)){
                if(!self){
                    self = true;//except self
                    continue;
                }else{
                    same = pwd;
                    break;
                }
            }
            int samecount = Utils.compareString(pwd, temp);
            if(samecount > max_count){
                max_count = samecount;
                same = temp;
            }
        }
        return same;
    }

    public int getSafetyFactor(String passwd){
        int score = 0;
        if(passwd == null || passwd.trim() == "")
            return Const.PWD_SAFETY_FACTOR_BAD;
        if(passwd.length() > 20)
            ++score;
        boolean hasNum = false,hasUp = false,hasLow = false,hasOther = false;
        for(int i = 0;i<passwd.length();i++){
            char x = passwd.charAt(i);
            if(!hasNum && x >= '0' && x <= '9'){
                hasNum = true;
                ++score;
            }else if(!hasLow && x >= 'a' && x <= 'z'){
                hasLow = true;
                ++score;
            }else if(!hasUp && x >= 'A' && x <= 'Z'){
                hasUp = true;
                ++score;
            }else if(!hasOther){
                hasOther = true;
                ++score;
            }
        }
        if(score > 4)
            return Const.PWD_SAFETY_FACTOR_BEST;
        else if(score > 3)
            return Const.PWD_SAFETY_FACTOR_GOOD;
        else if(score > 2)
            return Const.PWD_SAFETY_FACTOR_OK;
        else
            return Const.PWD_SAFETY_FACTOR_BAD;

    }


    public boolean insertPasswd(String name,String passwd,TypeBeen type){
        ContentValues values = new ContentValues();
        String uuid = Utils.getUUID();
        values.put("id", uuid);
        values.put("name", name);
        values.put("passwd", passwd);
        values.put("type", type.getName());
        SQLiteDatabase database = SQLUtils.getDataBase(ctx);
        int result = (int) database.insert(Const.TABLE_PASSWD_NAME, null, values);
        if(result > 0){
            change_flag = true;
        }
        return result>0;
    }


    public boolean updatePasswdType(String id, String type){
        if(id == null || id.equals(""))
            return false;
        ContentValues values = new ContentValues();
        if(type != null)
            values.put("type", type);
        if(values.size() == 0)
            return false;
        SQLiteDatabase database = SQLUtils.getDataBase(ctx);
        long result = database.update(Const.TABLE_PASSWD_NAME, values, "id == ?", new String[]{id});
        if(result > 0){
            change_flag = true;
        }
        return result > 0;
    }

    public boolean deletePasswd(String id){
        if(id == null || id.equals(""))
            return false;
        SQLiteDatabase database = SQLUtils.getDataBase(ctx);
        long result = database.delete(Const.TABLE_PASSWD_NAME, "id == ?", new String[]{id});
        if(result > 0){
            change_flag = true;
        }
        return result > 0;
    }

    public boolean deletePasswdByType(String type){
        if(type == null || type.equals(""))
            return false;
        SQLiteDatabase database = SQLUtils.getDataBase(ctx);
        long result = database.delete(Const.TABLE_PASSWD_NAME, "type == ?", new String[]{type});
        if(result > 0){
            change_flag = true;
        }
        return result > 0;
    }

    public boolean deleteAllPasswd(){
        SQLiteDatabase database = SQLUtils.getDataBase(ctx);
        long result = database.delete(Const.TABLE_PASSWD_NAME, null, null);
        if(result > 0){
            change_flag = true;
        }
        return result > 0;
    }

}
