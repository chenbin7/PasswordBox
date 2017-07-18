package com.cb.passwdbox.type;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.cb.passwdbox.R;
import com.cb.passwdbox.greendao.DBHelper;
import com.cb.passwdbox.greendao.model.PwdType;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by chenbin on 2017/7/16.
 */

public class TypeAdaptor extends RecyclerView.Adapter<TypeViewHolder> {

    private static final String TAG = "TypeAdaptor";

    List<PwdType> list;
    Context context;

    public TypeAdaptor(List<PwdType> list, Context context){
        this.list = list;
        this.context = context;
    }

    public void setList(List<PwdType> list){
        if(list == null){
            return;
        }
        this.list = list;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(TypeViewHolder holder, int position) {
        PwdType bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.pwd.setText(bean.getDescriptor());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditClick(position);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteClick(position);
            }
        });
        holder.linerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        onFingerDown(view, position, (int)motionEvent.getX());
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        onFingerCancel(view, position, (int)motionEvent.getX());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        onFingerMove(view, position, (int)motionEvent.getX());
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        onFingerUp(view, position, (int)motionEvent.getX());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_type_view,parent,false);
        TypeViewHolder holder = new TypeViewHolder(view);
        return holder;
    }

    private int mDistanceMax;
    private int mPointX = 0;
    private int mDistanceX = 0;
    private int mCurrentPosition = 0;
    private View mCurrentView = null;
    private Interpolator interpolater = new AccelerateDecelerateInterpolator();
    public void onFingerDown(View view, int position, int x){
        Log.d(TAG, "onFingerDown: position:"+position+"   x:"+x);
        if(mCurrentPosition == position){
            return;
        }
        if(mCurrentView != null){
            resetLocation(view);
        }
        mCurrentView = view;
        mPointX = x;
        mDistanceX = 0;
        mCurrentPosition = position;
    }

    public void onFingerUp(View view, int position, int x){
        Log.d(TAG, "onFingerUp: position:"+position+"   x:"+x);
        if(position != mCurrentPosition){
            return;
        }
        int distance = x - mPointX;
        Log.d(TAG, "onFingerUp:  distance = "+distance);
        if(distance >= 0){
            setViewX(view, 0);
            return;
        }
        if((distance + mDistanceMax) < (mDistanceMax / 2)){
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            view.setX(-mDistanceMax);
                        }
                    });
        } else {
            mCurrentView = null;
            mCurrentPosition = -1;
            mPointX = -1;
            resetLocation(view);
        }

    }

    public void onFingerMove(View view, int position, int x){
        Log.d(TAG, "onFingerMove: position:"+position+"   x:"+x);
        if(position != mCurrentPosition){
            return;
        }
        int distance = x - mPointX;
        Log.d(TAG, "onFingerMove:  distance = "+distance);
        if(distance > 0){
            mDistanceX = distance;
            setViewX(view, 0);
            return;
        }
        if((distance + mDistanceMax) <= 0){
            distance = -mDistanceMax;
        }
        mDistanceX = distance;
        setViewX(view, mDistanceX);
    }

    public void onFingerCancel(View view, int position, int x){
        Log.d(TAG, "onFingerCancel: position:"+position+"   x:"+x);
        mCurrentView = null;
        mCurrentPosition = -1;
        mPointX = -1;
        resetLocation(view);
    }

    private void setViewX(View view, int offset){
        Log.e(TAG, "setViewX: offset:"+offset +"  x:"+view.getX());
        if(view.getX() == offset){
            return;
        }
        if((view.getX() + offset) >= 0){
            return;
        }
        Observable.just(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        view.setX(offset);
                    }
                });
    }

    public void resetLocation(View view){
        if(view.getX() == 0){
            return;
        }
        Observable.just(view)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<View>() {
                    @Override
                    public void accept(@NonNull View view) throws Exception {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationX", view.getX(), 0);
                        anim.setDuration(500);
                        anim.setInterpolator(interpolater);
                        anim.start();
                    }
                });
    }

    public void onEditClick(int position) {
        Log.e(TAG, "onEditClick:  "+position );
    }

    public void onDeleteClick(int position){
        Log.e(TAG, "onDeleteClick:  "+position );
        Dialog dialog = new AlertDialog.Builder(context)
                .setTitle("您确认要删除该密码分类吗？")
                .setPositiveButton("同时删除密码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper.getInstance(context).deleteType(list.get(position), false);
                        deleteType(position);
                    }
                })
                .setNegativeButton("保留密码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper.getInstance(context).deleteType(list.get(position), true);
                        deleteType(position);
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        dialog.show();
    }

    private void deleteType(int position){
        list.remove(position);
        notifyDataSetChanged();
    }

    public void setBtnWidth(float dimension) {
        mDistanceMax = (int) (dimension * 2);
    }
}
