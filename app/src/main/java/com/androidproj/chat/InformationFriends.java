package com.androidproj.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Nguyen Ba Thai on 2/4/2017.
 */

public class InformationFriends extends AppCompatActivity {
    private DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    private TextView viewUsername, viewEmail, viewUid;
    private Toolbar toolbar;
    private String myuid, key;
    private NoteUser noteuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_friends);
        viewUsername = (TextView) findViewById(R.id.tvUsername);
        viewEmail = (TextView) findViewById(R.id.tvEmail);
        loadData();
        setTitle("Thông tin "+ viewUsername.getText());
        toolbar = (Toolbar) findViewById(R.id.tbInformationUser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    private void loadData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DataUser");
        noteuser = (NoteUser) bundle.getSerializable("NoteUser");
        viewEmail.setText(noteuser.getEmail());
        viewUsername.setText(noteuser.getUsername());
        myuid = bundle.getString("myuid");
        getkey();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmessege, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.btnInboxWithFriend:
                chatwith();
                Bundle b = new Bundle();
                b.putString("myuid", myuid);
                b.putString("key", key);
                b.putString("name", noteuser.getUsername());
                Intent it = new Intent(InformationFriends.this, Chat.class);
                it.putExtra("con", b);
                //Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
                finish();
                startActivity(it);
                break;
            default:
                break;
        }

        return true;
    }

    private void chatwith() {
        data.child("Messeger").child(key).child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    data.child("Users").child(myuid).child("listMesseger").child(key).setValue(key);
                    data.child("Users").child(noteuser.getUid()).child("listMesseger").child(key).setValue(key);
                    ArrayList<String> arr = new ArrayList<>();
                    arr.add(myuid);
                    arr.add(noteuser.getUid());
                    Conversation c = new Conversation(noteuser.getUsername(), key, arr, null);
                    data.child("Messeger").child(key).setValue(c);
                    data.goOffline();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        data.goOffline();

    }

    private void getkey() {
        if(myuid.compareTo(noteuser.getUid()) > 0) key = myuid + noteuser.getUid();
        else key = noteuser.getUid() + myuid;
    }

}
