package com.cb.passwdbox.modifypwd;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.cb.passwdbox.R;
import com.cb.passwdbox.database.SPUtils;

public class ModifyPwdActivity extends AppCompatActivity {
    private static final String TAG = "ModifyPwdActivity";

    EditText pwdText,checkText;
    SPUtils utils;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modify_pwd);

        pwdText = (EditText) findViewById(R.id.pwd_new);
        checkText = (EditText) findViewById(R.id.pwd_check);
        context = getApplicationContext();
        utils = new SPUtils(context);

    }

    public void onBtnClick(View view) {
        String pwd = pwdText.getText().toString();
        String check = checkText.getText().toString();
        Log.d(TAG,"pwd = "+pwd+"    check = "+check);

        if(pwd.equals(check)){
            SPUtils utils = new SPUtils(context);
            utils.savePasswd(pwd);
            ModifyPwdActivity.this.finish();
        }else {
            showMsg(getApplicationContext().getString(R.string.set_check_error));
            pwdText.setText("");
            checkText.setText("");
        }
    }

    public void showMsg(String msg){
        Snackbar snackbar = Snackbar.make(ModifyPwdActivity.this.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}
