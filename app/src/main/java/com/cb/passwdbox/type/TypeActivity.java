package com.cb.passwdbox.type;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.cb.passwdbox.R;
import com.cb.passwdbox.activity.SettingActivity;
import com.cb.passwdbox.greendao.DBHelper;
import com.cb.passwdbox.greendao.model.PwdType;

import java.util.List;

public class TypeActivity extends AppCompatActivity {
    private static final String TAG = "TypeActivity";

    Toolbar mToolbar;
    RecyclerView typeListView;
    Context context;
    List<PwdType> list;
    TypeAdaptor adaptor;

    private static final int REQUEST_CODE_ADD_TYPE = 1;
//    private static final int REQUEST_CODE_ADD_PWD = 2;
    private static final int REQUEST_CODE_SETTING = 3;

//    List<String> type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_type);
        context = getApplicationContext();
        mToolbar = (Toolbar) findViewById(R.id.type_toolbar);
        initToolbar();
        typeListView = (RecyclerView) findViewById(R.id.list_pwd);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        list = DBHelper.getInstance(getApplicationContext()).getTypes();
        adaptor.setList(list);
        typeListView.setAdapter(adaptor);
    }

    public void doAddType() {
        Log.d(TAG, "doAddType: ");
        Intent intent = new Intent();
        intent.setClass(TypeActivity.this,AddTypeActivity.class);
        startActivityForResult(intent,REQUEST_CODE_ADD_TYPE);
    }

    public void goSetting(){
        Log.d(TAG, "goSetting: ");
        Intent intent = new Intent();
        intent.setClass(TypeActivity.this,SettingActivity.class);
        startActivityForResult(intent,REQUEST_CODE_SETTING);
    }

    private void initToolbar() {
        mToolbar.setTitle("类别");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_add_type:
                        doAddType();
                        break;
                    case R.id.action_settings:
                        goSetting();
                        break;
                }
                Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_type, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){
        list = DBHelper.getInstance(getApplicationContext()).getTypes();
        typeListView.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new TypeAdaptor(list,this);
        adaptor.setBtnWidth(getApplicationContext().getResources().getDimension(R.dimen.list_item_btn_width));
    }


}

