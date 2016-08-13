package com.cb.passwdbox.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Message;

/**
 * Created by chenbin on 16-8-13.
 */
public abstract class Presenter {
    protected Activity activity;
    protected PresenterUpdateViewListener listener;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setListener(PresenterUpdateViewListener listener) {
        this.listener = listener;
    }

    protected Activity getActivity() {
        return activity;
    }
    protected Context getContext(){
        return activity.getApplicationContext();
    }

    public interface PresenterUpdateViewListener{
        public void updateView(Message msg);
    }

}
