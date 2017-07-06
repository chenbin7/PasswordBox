package com.cb.passwdbox.type;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cb.passwdbox.R;
import com.cb.passwdbox.activity.AddPwdActivity;
import com.cb.passwdbox.activity.AddTypeActivity;
import com.cb.passwdbox.activity.SettingActivity;
import com.cb.passwdbox.been.PasswdBeen;
import com.cb.passwdbox.database.SPUtils;
import com.cb.passwdbox.presenter.MainPresenter;
import com.cb.passwdbox.presenter.PresenterFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "[pwdBox]main";

    Toolbar mToolbar;
    RecyclerView pwdListView;
    SPUtils utils;
    Context context;
    MainPresenter presenter;
    private static final int REQUEST_CODE_ADD_TYPE = 1;
    private static final int REQUEST_CODE_ADD_PWD = 2;
    private static final int REQUEST_CODE_SETTING = 3;

//    List<String> type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        mToolbar = (Toolbar) findViewById(R.id.type_toolbar);
        initToolbar();
        pwdListView = (RecyclerView) findViewById(R.id.list_pwd);
        init();
    }

    public void doAddType() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,AddTypeActivity.class);
        startActivityForResult(intent,REQUEST_CODE_ADD_TYPE);
    }

    public void goSetting(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,SettingActivity.class);
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
        presenter = (MainPresenter) PresenterFactory.createPresenter(MainPresenter.class,this);
        List<TypeBeen> list = presenter.initType();
        pwdListView.setLayoutManager(new LinearLayoutManager(this));
        pwdListView.setAdapter(new PwdAdapter(list,this));
    }

    //List
    class PwdAdapter extends RecyclerView.Adapter<PwdViewHolder>{

        List<TypeBeen> list;
        Context context;
        public PwdAdapter(List<TypeBeen> list,Context context){
            this.list = list;
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onBindViewHolder(PwdViewHolder holder, int position) {
            TypeBeen bean = list.get(position);
            holder.name.setText(bean.getName());
            holder.pwd.setText(bean.getDescriptor());
        }

        @Override
        public PwdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_type_view,parent,false);
            PwdViewHolder holder = new PwdViewHolder(view);
            return holder;
        }
    }

    class PwdViewHolder extends RecyclerView.ViewHolder {

        TextView name,pwd;

        public PwdViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.type_name);
            pwd = (TextView) view.findViewById(R.id.type_descriptor);
        }
    }

}

