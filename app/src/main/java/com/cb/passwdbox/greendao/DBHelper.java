package com.cb.passwdbox.greendao;

import android.content.Context;
import android.util.Log;

import com.cb.passwdbox.greendao.db.DaoMaster;
import com.cb.passwdbox.greendao.db.DaoSession;
import com.cb.passwdbox.greendao.db.PasswordDao;
import com.cb.passwdbox.greendao.db.PwdTypeDao;
import com.cb.passwdbox.greendao.model.Password;
import com.cb.passwdbox.greendao.model.PwdType;
import com.cb.passwdbox.property.Const;
import com.cb.passwdbox.utils.Utils;

import java.util.List;

/**
 * Created by chenbin on 2017/7/8.
 */

public class DBHelper {
    private static final String TAG = "DBHelper";

    private static DBHelper sDBHelper;

    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private DaoMaster mMaster;
    private DaoSession mSession;
    private PwdTypeDao mTypeDao;
    private PasswordDao mPasswdDao;
    private String mDefultTypeId = null;

    public synchronized static DBHelper getInstance(Context context) {
        if (sDBHelper == null) {
            sDBHelper = new DBHelper(context);
        }
        return sDBHelper;
    }

    private DBHelper(Context context) {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, Const.DATABASE_NAME, null);
        mMaster = new DaoMaster(mDevOpenHelper.getWritableDb());
        mSession = mMaster.newSession();
        mTypeDao = mSession.getPwdTypeDao();
        mPasswdDao = mSession.getPasswordDao();
        getDefulatTypeId();
    }

    private void getDefulatTypeId(){
        List<PwdType> list = mTypeDao.queryBuilder().where(PwdTypeDao.Properties.Name.eq(Const.DEFAULT_TYPE_NAME)).orderAsc(PwdTypeDao.Properties.Id).list();
        if(list != null && list.size() > 0){
            mDefultTypeId = list.get(0).getId();
        } else {
            insertDefultType();
            list = mTypeDao.queryBuilder().where(PwdTypeDao.Properties.Name.eq(Const.DEFAULT_TYPE_NAME)).orderAsc(PwdTypeDao.Properties.Id).list();
            if(list != null && list.size() > 0){
                mDefultTypeId = list.get(0).getId();
            } else {
                Log.e(TAG, "DBHelper: can't get the defult type");
                mDefultTypeId = null;
            }
        }
    }

    private void insertDefultType(){
        PwdType type = new PwdType();
        type.setName(Const.DEFAULT_TYPE_NAME);
        type.setDescriptor("未分类密码");
        type.setId(Utils.getUUID());
        insertType(type);
    }



    public boolean insertType(PwdType type) {
        if (type == null)
            return false;
        long result = mTypeDao.insert(type);
        Log.i(TAG, "insertType: " + result);
        return result > 0;
    }

    public boolean updateType(PwdType type) {
        if (type == null)
            return false;
        long result = mTypeDao.insertOrReplace(type);
        Log.i(TAG, "updateType: " + result);
        return result > 0;
    }

    public void deleteType(PwdType type, boolean keepPwd) {
        if (type == null)
            return;
        List<Password> pwds = getPasswdByType(type);
        Log.w(TAG, "deleteTypeOnly: "+pwds );
        if(pwds != null){
            if(keepPwd && mDefultTypeId != null){
                for (Password pwd : pwds) {
                    pwd.setTypeId(mDefultTypeId);
                    updatePasswd(pwd);
                }
            } else {
                for (Password pwd : pwds) {
                    deletePasswd(pwd);
                }
            }
        }
        mTypeDao.delete(type);
    }

    public List<PwdType> getTypes() {
        return mTypeDao.queryBuilder().list();
    }

    public boolean insertPasswd(Password pwd) {
        if (pwd == null)
            return false;
        long result = mPasswdDao.insert(pwd);
        Log.i(TAG, "insertPasswd: " + result);
        return result > 0;
    }

    public boolean updatePasswd(Password pwd) {
        if (pwd == null)
            return false;
        long result = mPasswdDao.insertOrReplace(pwd);
        Log.i(TAG, "updatePasswd: " + result);
        return result > 0;
    }

    public void deletePasswd(Password pwd) {
        if (pwd == null)
            return;
        mPasswdDao.delete(pwd);
    }

    public List<Password> getPasswdByType(PwdType type){
        if(type == null)
            return null;
        return mPasswdDao.queryBuilder().where(PasswordDao.Properties.TypeId.eq(type.getId())).list();
    }


    public void clear() {
        mTypeDao.deleteAll();
        mPasswdDao.deleteAll();
        getDefulatTypeId();
    }
}
