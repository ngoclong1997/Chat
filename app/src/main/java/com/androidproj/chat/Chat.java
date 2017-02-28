package com.androidproj.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by pthhung on 26/02/2017.
 */

public class Chat extends AppCompatActivity {

    private Bundle b;
    private String myUid, key, withName, myUserName;
    private ListView lst;
    private AdapterMessege adapter;
    private DatabaseReference data;
    private ArrayList<Message> arr;
    private ImageButton btnSend;
    private EditText edtChat;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getFormWidgets();
        addEventFormWidgets();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnSetting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btnAddPeopleForChat:
                Toast.makeText(this, "add people", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addEventFormWidgets() {
        data.child("Users").child(myUid).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myUserName = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        data.child("Messeger").child(key).child("lstMesseger").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arr.add(dataSnapshot.getValue(Message.class));
                adapter.notifyDataSetChanged();
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

        btnSend.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(edtChat.getText().toString() != null && !edtChat.getText().toString().equals(""))
                {
                    Message m = new Message(edtChat.getText().toString(), myUserName, myUid);
                    data.child("Messeger").child(key).child("lstMesseger").push().setValue(m);
                }
                edtChat.setText("");
            }
        });

    }

    private void getFormWidgets() {

        data = FirebaseDatabase.getInstance().getReference();
        b = getIntent().getBundleExtra("user");
        key = b.getString("key");
        myUid = b.getString("uid");
        withName = b.getString("chatWithName");
        edtChat = (EditText) findViewById(R.id.edtChatText);
        btnSend = (ImageButton) findViewById(R.id.btnSend);


        toolbar = (Toolbar) findViewById(R.id.tbChat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        lst = (ListView) findViewById(R.id.lstvChat);
        arr = new ArrayList<>();
        adapter = new AdapterMessege(this, R.layout.adapter_messeger, arr, myUid);
        lst.setAdapter(adapter);
        setTitle(withName);
    }
}
