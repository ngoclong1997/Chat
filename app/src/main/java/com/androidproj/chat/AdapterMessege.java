package com.androidproj.chat;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pthhung on 26/02/2017.
 */

public class AdapterMessege extends ArrayAdapter<Message> {
    private Activity context = null;
    private ArrayList<Message> arr = null;
    private int layoutID;
    private String myUID;

    public AdapterMessege(Activity context, int layoutID, ArrayList<Message> objects, String myUID) {
        super(context, layoutID, objects);
        this.context = context;
        this.arr = objects;
        this.layoutID = layoutID;
        this.myUID = myUID;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutID, null);
        if(arr.size() > 0 && position >= 0){
            final  Message m = arr.get(position);
            final TextView tvChat, tvName;
            if (myUID.equals(m.getUid().toString())){
                tvChat = (TextView) convertView.findViewById(R.id.tvMessegeOfMe);
                tvName = (TextView) convertView.findViewById(R.id.tvNameOfMe);
            }else {
                tvChat = (TextView) convertView.findViewById(R.id.tvMessegeOfOther);
                tvName = (TextView) convertView.findViewById(R.id.tvNameOfOther);
            }

            tvChat.setText(m.getMsg().toString());
            tvChat.setVisibility(View.VISIBLE);
            tvName.setText(m.getUsername().toString());
            tvName.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
