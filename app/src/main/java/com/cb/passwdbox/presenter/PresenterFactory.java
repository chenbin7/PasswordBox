package com.cb.passwdbox.presenter;

import android.app.Activity;

/**
 * Created by chenbin on 16-8-13.
 */
public class PresenterFactory {

    public static Presenter createPresenter(Class clazz,Activity activity){
        Presenter presenter = null;
        try {
            presenter = (Presenter) clazz.newInstance();
            presenter.setActivity(activity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return presenter;
    }
}
