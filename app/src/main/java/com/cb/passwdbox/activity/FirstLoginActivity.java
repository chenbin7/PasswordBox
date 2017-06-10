package com.cb.passwdbox.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cb.passwdbox.R;
import com.cb.passwdbox.database.SPUtils;

public class FirstLoginActivity extends AppCompatActivity {
    private static final String TAG = "[pwdBox]firstlogin";

    EditText pwdText,checkText;
    TextView errorText;
    Button commitBtn;
    SPUtils utils;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_new_pwd);

        pwdText = (EditText) findViewById(R.id.pwd_new);
        checkText = (EditText) findViewById(R.id.pwd_check);
        errorText = (TextView) findViewById(R.id.checkerr);
        commitBtn = (Button) findViewById(R.id.commit);

        errorText.setVisibility(View.INVISIBLE);
        context = getApplicationContext();
        utils = new SPUtils(context);

        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = pwdText.getText().toString();
                String check = checkText.getText().toString();
                Log.d(TAG,"pwd = "+pwd+"    check = "+check);
                if(pwd.equals(check)){
                    SPUtils utils = new SPUtils(context);
                    utils.savePasswd(pwd);
                    FirstLoginActivity.this.finish();
                }else {
                    errorText.setVisibility(View.VISIBLE);
                    pwdText.setText("");
                    checkText.setText("");
                }
            }
        });
    }
}
