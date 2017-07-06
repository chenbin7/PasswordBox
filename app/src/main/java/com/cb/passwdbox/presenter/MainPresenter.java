package com.cb.passwdbox.presenter;

import com.cb.passwdbox.been.PasswdBeen;
import com.cb.passwdbox.type.TypeBeen;
import com.cb.passwdbox.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbin on 16-8-14.
 */
public class MainPresenter extends Presenter {

    public List<PasswdBeen> initPwd(){
        List<PasswdBeen> list = new ArrayList<PasswdBeen>();
        for (int i = 0; i < 20; i++) {
            String id = Utils.getUUID();
            String type = "default";
            list.add(new PasswdBeen(id,"name"+i,"passwd"+i,type));
        }
        return list;
    }
    public List<TypeBeen> initType(){
        List<TypeBeen> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String id = Utils.getUUID();
            String name = "type"+i;
            String descr = "这是第"+i+"中类型的密码";
            list.add(new TypeBeen(id, name, descr));
        }
        return list;
    }
}
