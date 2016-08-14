package com.cb.passwdbox.presenter;

import com.cb.passwdbox.been.PasswdBeen;
import com.cb.passwdbox.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbin on 16-8-14.
 */
public class MainPresenter extends Presenter {

    public List<PasswdBeen> initPwd(){
        List<PasswdBeen> list = new ArrayList<PasswdBeen>();
        for (int i = 0; i < 10; i++) {
            String id = Utils.getUUID();
            String type = "default";
            list.add(new PasswdBeen(id,"name"+i,"passwd"+i,type));
        }
        return list;
    }
    public String[] initType(){
        String[] list = new String[10];
        for (int i = 0; i < 10; i++) {
            list[i] = "Type"+i;
        }
        return list;
    }
}
