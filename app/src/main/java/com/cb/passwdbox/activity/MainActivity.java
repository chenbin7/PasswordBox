package com.cb.passwdbox.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.cb.passwdbox.R;
import com.cb.passwdbox.been.PasswdBeen;
import com.cb.passwdbox.database.SPUtils;
import com.cb.passwdbox.presenter.MainPresenter;
import com.cb.passwdbox.presenter.PresenterFactory;

import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "[pwdBox]main";

    Button addTypeText,settingText;
    Spinner typeSpinner;
    RecyclerView pwdListView;
    Button addPwdBtn;
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

        addTypeText = (Button) findViewById(R.id.add_type);
        settingText = (Button) findViewById(R.id.setting);
        typeSpinner = (Spinner) findViewById(R.id.type);
        pwdListView = (RecyclerView) findViewById(R.id.list_pwd);
        addPwdBtn = (Button) findViewById(R.id.add_pwd);

        init();
        addTypeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,AddTypeActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ADD_TYPE);
            }
        });
        settingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SettingActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SETTING);
            }
        });

        addPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,AddPwdActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ADD_PWD);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){
        presenter = (MainPresenter) PresenterFactory.createPresenter(MainPresenter.class,this);
        String[] items = presenter.initType();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG,"spinner  select  view="+view+"   i="+i+"   l="+l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(TAG,"spinner  nothing");
            }
        });

        List<PasswdBeen> list = presenter.initPwd();
        pwdListView.setLayoutManager(new LinearLayoutManager(this));
        pwdListView.setAdapter(new PwdAdapter(list,this));
    }

    //List
    class PwdAdapter extends RecyclerView.Adapter<PwdViewHolder>{

        List<PasswdBeen> list;
        Context context;
        public PwdAdapter(List<PasswdBeen> list,Context context){
            this.list = list;
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onBindViewHolder(PwdViewHolder holder, int position) {
            PasswdBeen bean = list.get(position);
            holder.name.setText(bean.getName());
            holder.pwd.setText(bean.getPasswd());
            holder.detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        @Override
        public PwdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_pwd_view,parent,false);
            PwdViewHolder holder = new PwdViewHolder(view);
            return holder;
        }
    }

    class PwdViewHolder extends RecyclerView.ViewHolder {

        TextView name,pwd;
        ImageButton detail;

        public PwdViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            pwd = (TextView) view.findViewById(R.id.pwd);
            detail = (ImageButton) view.findViewById(R.id.detail);
        }
    }

}
