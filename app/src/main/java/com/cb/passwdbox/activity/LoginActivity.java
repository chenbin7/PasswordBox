package com.cb.passwdbox.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cb.passwdbox.R;
import com.cb.passwdbox.been.PasswdManager;
import com.cb.passwdbox.database.SPUtils;
import com.cb.passwdbox.property.Const;

public class LoginActivity extends Activity {
    private static final String TAG = "[pwdBox]login";

    EditText pwdText;
    TextView errorText;
    Button commitBtn;
    SPUtils utils;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        utils = new SPUtils(context);
        if(utils.isFirst()){
            goFirstLoginActivity();
        }

        pwdText = (EditText) findViewById(R.id.pwd);
        errorText = (TextView) findViewById(R.id.pwderr);
        commitBtn = (Button) findViewById(R.id.commit);
        errorText.setVisibility(View.INVISIBLE);
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = pwdText.getText().toString();
                Log.d(TAG,"pwd = "+pwd);
                int mode = utils.getLoginMode();
                if(mode == Const.LOGIN_MODE_GRABLED){
                    goMainActivity(pwd);
                    return;
                }else{
                    int max = utils.getLoginTryMaxCount();
                    int errCount = utils.getLoginWrongCount();
                    if(errCount >= max) {
                        PasswdManager manager = PasswdManager.getInstance(context);
                        manager.deleteAllPasswd();
                        manager = null;
                    }
                }
                boolean right = utils.isPasswdRight(pwd);
                Log.d(TAG,"pwd right = "+right);
                if(right){
                    utils.saveLoginWrongCount(0);
                    goMainActivity(pwd);
                }else{
                    pwdText.setText("");
                    errorText.setVisibility(View.VISIBLE);
                    doPwdError();
                }
            }
        });
    }

    private void goFirstLoginActivity(){
        Intent intent = new Intent();
        intent.setClass(context,FirstLoginActivity.class);
        startActivity(intent);
    }

    private void goMainActivity(String pwd){
        Intent intent = new Intent();
        intent.putExtra("pwd",pwd);
        intent.setClass(context,MainActivity.class);
        startActivity(intent);
    }

    private void doPwdError(){
        int max = utils.getLoginTryMaxCount();
        int errCount = utils.getLoginWrongCount();
        ++errCount;
        Log.d(TAG,"doPwdError   max = "+max+"   errcount = "+errCount);
        if(errCount >= max){
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("提示");
            builder.setMessage("密码输入错误次数超过设置，现将清除所有密码记录");
            builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    PasswdManager manager = PasswdManager.getInstance(context);
                    manager.deleteAllPasswd();
                    manager = null;
                }
            });
            builder.create().show();
            return;
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
                }
            });
            builder.create().show();
        }
        utils.saveLoginWrongCount(errCount);
    }


}
