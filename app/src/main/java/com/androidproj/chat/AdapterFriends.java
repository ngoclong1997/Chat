package com.androidproj.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nguyen Ba Thai on 3/3/2017.
 */

public class AdapterFriends extends ArrayAdapter<NoteUser> {
    Activity context =null;

    int layoutId;

    ArrayList<NoteUser> lsNotes =null;

    public AdapterFriends(Activity context, int resource, ArrayList<NoteUser> lsNotes) {
        super(context, resource, lsNotes);

        this.context = context;

        this.layoutId = resource;

        this.lsNotes = lsNotes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();

        convertView =inflater.inflate(layoutId,null);

        if(lsNotes.size()>0 && position >=0) {
            //lay cac control
            TextView name = (TextView) convertView.findViewById(R.id.tvname);

            TextView email = (TextView) convertView.findViewById(R.id.tvemail);

            RadioButton trangthai = (RadioButton) convertView.findViewById(R.id.radioButton);

            //lay duoc doi tuong note tai vi tri position
            NoteUser note = lsNotes.get(position);

                name.setText(note.getUsername());

                email.setText(note.getEmail());

                if (note.getActive() == Boolean.TRUE) {
                    trangthai.setChecked(true);
                } else {
                    trangthai.setChecked(false);
                }
        }
        return convertView;
    }
}
