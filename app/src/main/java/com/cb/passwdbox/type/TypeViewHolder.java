package com.cb.passwdbox.type;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cb.passwdbox.R;

/**
 * Created by chenbin on 2017/7/16.
 */

public class TypeViewHolder extends RecyclerView.ViewHolder {
    TextView name,pwd;
    ImageView delete,edit;
    LinearLayout linerLayout;

    public TypeViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.type_name);
        pwd = (TextView) view.findViewById(R.id.type_descriptor);
        delete = (ImageView) view.findViewById(R.id.type_item_delete);
        edit = (ImageView) view.findViewById(R.id.type_item_edit);
        linerLayout = (LinearLayout) view.findViewById(R.id.type_item_layout);
    }
}
