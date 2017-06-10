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

public class TypeManager {
    private static TypeManager manager;
    private static List<TypeBeen> list;
    private static Context ctx;
    private static boolean change_flag;

    public static TypeManager getInstance(Context context){
        if(manager == null){
            manager = new TypeManager();
            ctx = context;
            change_flag = false;
        }
        return manager;
    }

    private List<TypeBeen> getTypeBeens(Context context){
        SQLiteDatabase database = SQLUtils.getDataBase(context);
        List<TypeBeen> list = new ArrayList<TypeBeen>();
        Cursor cursor = database.query(Const.TABLE_TYPE_NAME, new String[]{"id","name"}, null, null, null, null, null);
        if(cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            list.add(new TypeBeen(name, id));
        }
        return list;
    }

    private void checkList(){
        if(!change_flag && list != null)
            return;
        getTypeBeens(ctx);
    }

    private boolean saveType(String id, String name){
        SQLiteDatabase database = SQLUtils.getDataBase(ctx);
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        long result = database.insert(Const.TABLE_TYPE_NAME, null, values);
        return result > 0;
    }

    public boolean saveType(String name){
        if(name == null || name.equals(""))
            return false;
        if(isTypeExit(name))
            return false;
        String uuid = Utils.getUUID();
        return saveType(uuid,name);
    }

    public boolean isTypeExit(String name){
        if(name == null || name.equals(""))
            return false;
        SQLiteDatabase database = SQLUtils.getDataBase(ctx);
        Cursor cursor = database.query(Const.TABLE_TYPE_NAME, new String[]{"id","count"}, "name == ?", new String[]{name}, null, null, null);
        return cursor.moveToFirst();
    }

    public List<String> getAllTypeNames(){
        checkList();
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            names.add(list.get(i).getName());
        }
        return names;
    }

    public boolean updateType(String oldName, String newName){
        if(oldName == null || oldName.equals("") || newName == null || newName.equals(""))
            return false;
        SQLiteDatabase database = SQLUtils.getDataBase(ctx);
        ContentValues values = new ContentValues();
        values.put("name", newName);
        int result = database.update(Const.TABLE_TYPE_NAME, values, "name == ?", new String[]{oldName});
        return result > 0;
    }
}
