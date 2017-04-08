package com.androidproj.chat;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterUserForCreate extends ArrayAdapter<User> {

    private Activity context = null;
    private int layoutID;
    private ArrayList<User> obj = null;
    private String myuid;
    public AdapterUserForCreate(Activity context, int layoutID, ArrayList<User> objects, String myuid) {
        super(context, layoutID, objects);
        this.myuid = myuid;
        this.context = context;
        this.layoutID = layoutID;
        this.obj = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutID, null);
        final  User m = obj.get(position);
        final TextView tvName = (TextView) convertView.findViewById(R.id.tvNameUserWhileCreateGroup);
        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.cbSelect);

        cb.setChecked(false);
        tvName.setText(m.getUsername());

        return convertView;

    }

}
