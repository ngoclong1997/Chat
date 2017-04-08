package com.androidproj.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddUserForGroup extends AppCompatActivity {

    private DatabaseReference data;
    private String key, myuid;
    private ArrayList<String> lstUser;
    private ListView viewUser;
    private Toolbar toolbar;
    private AdapterUserForCreate adtUser;
    private ArrayList<User> arrUser;
    public static final int RESULTCODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_for_group);

        getFromWidgets();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnCreateGroup:
                createGroup();
                return  true;
            case android.R.id.home:
                data.goOffline();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createGroup() {
        for(int i = 0; i < viewUser.getChildCount(); ++i){
            View v = viewUser.getChildAt(i);
            CheckBox c = (CheckBox) v.findViewById(R.id.cbSelect);
            if(c.isChecked()){
                String uid = arrUser.get(i).getUid();
                //data.child("Users").child(uid).child("listMesseger").child(key).setValue(key);
                lstUser.add(uid);
            }
        }
        //data.child("Messeger").child(key).child("user").setValue(lstUser);

        Bundle b = new Bundle();
        b.putStringArrayList("lst", lstUser);
        Intent it = getIntent();
        it.putExtra("ex", b);
        setResult(RESULTCODE, it);
        data.goOffline();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_create_group_chat, menu);
        MenuItem item = menu.getItem(0);
        item.setIcon(R.drawable.ic_save_black_24dp);
        return super.onCreateOptionsMenu(menu);
    }
    private void getFromWidgets() {
        Bundle b = getIntent().getBundleExtra("ex");
        lstUser = b.getStringArrayList("lst");
        key = b.getString("key");
        myuid = b.getString("myuid");
        if(lstUser.size() == 0){
//            lstUser = new ArrayList<>();
            lstUser.add(myuid);
        }
        toolbar = (Toolbar) findViewById(R.id.tbAddUserCreateGroup);
        setSupportActionBar(toolbar);
        viewUser = (ListView)findViewById(R.id.lstUserAdd);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        data = FirebaseDatabase.getInstance().getReference();

        arrUser = new ArrayList<>();
        adtUser = new AdapterUserForCreate(this, R.layout.adapter_user_for_create, arrUser, myuid);
        viewUser.setAdapter(adtUser);

        getdata();
    }

    private void getdata() {
        data.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User u = dataSnapshot.getValue(User.class);
                if (find(u.getUid())){
                    arrUser.add(u);
                }
                adtUser.notifyDataSetChanged();
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
    private boolean find(String uid) {

        for(int i = 0; i < lstUser.size(); ++i)
            if(uid.compareTo(lstUser.get(i).toString()) == 0) return false;
        return true;
    }
}
