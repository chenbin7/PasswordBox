package com.cb.passwdbox.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cb.passwdbox.R;
import com.cb.passwdbox.greendao.DBHelper;
import com.cb.passwdbox.type.TypeActivity;
import com.cb.passwdbox.modifypwd.ModifyPwdActivity;
import com.cb.passwdbox.database.SPUtils;
import com.cb.passwdbox.property.Const;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";

    EditText pwdText;
    SPUtils utils;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        utils = new SPUtils(context);
        if(utils.isFirst()){
            goModifyPwdActivity();
        }
        pwdText = (EditText) findViewById(R.id.pwd);
    }

    public void onBtnClick(View view) {
        String pwd = pwdText.getText().toString();
        Log.d(TAG,"pwd = "+pwd);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        int mode = utils.getLoginMode();
        if(mode == Const.LOGIN_MODE_GRABLED){
            goMainActivity(pwd);
            return;
        }else{
            int max = utils.getLoginTryMaxCount();
            int errCount = utils.getLoginWrongCount();
            if(errCount >= max) {
                doPwdError();
                return;
            }
        }
        boolean right = utils.isPasswdRight(pwd);
        Log.d(TAG,"pwd right = "+right);
        if(right){
            utils.saveLoginWrongCount(0);
            goMainActivity(pwd);
        }else{
            pwdText.setText("");
            if(!doPwdError()) {
                showMsg(view, getApplicationContext().getString(R.string.login_pwderror));
            }
        }
    }

    public void showMsg(View view, String msg){
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void goModifyPwdActivity(){
        Intent intent = new Intent();
        intent.setClass(context, ModifyPwdActivity.class);
        startActivity(intent);
    }

    private void goMainActivity(String pwd){
        Intent intent = new Intent();
        intent.putExtra("pwd",pwd);
        intent.setClass(context,TypeActivity.class);
        startActivity(intent);
    }

    private boolean doPwdError(){
        int max = utils.getLoginTryMaxCount();
        int errCount = utils.getLoginWrongCount();
        ++errCount;
        utils.saveLoginWrongCount(errCount);
        Log.d(TAG,"doPwdError   max = "+max+"   errcount = "+errCount);
        if(errCount >= max){
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("提示");
            builder.setMessage("密码输入错误次数超过设置，现将清除所有密码记录");
            builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DBHelper.getInstance(null).clear();
                    utils.saveLoginWrongCount(0);
                }
            });
            builder.create().show();
            return true;
        }
        final int times = max - errCount;
        if(times <= 3){
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("警告！");
            builder.setMessage("您还有"+times+"次密码输入机会，之后将清除所有密码！");
            builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i(TAG,"login err: times = "+times);
                    //TODO
                }
            });
            builder.create().show();
            return true;
        }
        return false;
    }


}
