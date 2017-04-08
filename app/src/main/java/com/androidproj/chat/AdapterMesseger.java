package com.androidproj.chat;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Nguyen Ba Thai on 31/3/2017.
 */

public class AdapterMesseger extends ArrayAdapter<NoteConversation> {
    private Activity context = null;
    private int layoutId;
    private ArrayList<NoteConversation> lsNotes = null;
     DatabaseReference databaseReference;
    private TextView viewnamemessger,viewlastmessger;

    public AdapterMesseger(Activity context, int resource, ArrayList<NoteConversation> lsNotes) {
        super(context, resource, lsNotes);
        this.context = context;
        this.layoutId = resource;
        this.lsNotes = lsNotes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if (lsNotes.size() > 0 && position >= 0) {

            //lay cac control
           viewnamemessger = (TextView) convertView.findViewById(R.id.viewnamemesseger);
              viewlastmessger =(TextView) convertView.findViewById(R.id.viewlastmessger);
            //lay duoc doi tuong note tai vi tri position
            NoteConversation note = lsNotes.get(position);
            viewnamemessger.setText(note.getNameConversation());

            databaseReference.child("Messeger").child(note.getConversationID()).child("lstMesseger").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                     viewlastmessger.setText(dataSnapshot.child("msg").getValue().toString());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
        return convertView;
    }

}
