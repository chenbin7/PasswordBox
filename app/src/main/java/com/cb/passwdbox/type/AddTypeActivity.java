package com.cb.passwdbox.type;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cb.passwdbox.R;
import com.cb.passwdbox.greendao.DBHelper;
import com.cb.passwdbox.greendao.model.PwdType;
import com.cb.passwdbox.utils.Utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by chenbin on 16-8-14.
 */
public class AddTypeActivity extends Activity {
    private static final String TAG = "AddTypeActivity";

    private EditText mTypeName;
    private EditText mTypeDescriptor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
        mTypeName = (EditText) findViewById(R.id.type_name);
        mTypeDescriptor = (EditText) findViewById(R.id.type_descriptor);

    }

    public void onBtnClick(View view){
        String name = mTypeName.getText().toString();
        String descriptor = mTypeDescriptor.getText().toString();
        Log.e(TAG, "onBtnClick: name:"+name+"    descriptor:"+descriptor);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if(name == null || name.trim().toString().equals("")){
            showMsg(view, "请输入密码分类");
            return;
        }
        if(descriptor == null || descriptor.trim().toString().equals("")){
            showMsg(view, "请输入分类描述");
            return;
        }
        String id = Utils.getUUID();
        PwdType type = new PwdType(id, name, descriptor);
        if(DBHelper.getInstance(getApplicationContext()).insertType(type)) {
            showMsg(view, "添加分类成功");
            Observable.just(0)
                    .delay(2, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            AddTypeActivity.this.finish();
                        }
                    });
        } else {
            showMsg(view, "添加分类失败");
        }
    }

    public void showMsg(View view, String msg){
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}
