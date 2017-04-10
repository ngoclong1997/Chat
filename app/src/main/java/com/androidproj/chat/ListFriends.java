package com.androidproj.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ListFriends extends AppCompatActivity {
    private Toolbar toolbar;
    private Bundle b;
    private String myUid;
    private DatabaseReference databaseReference;
    private ListView listView;
    private ArrayList<NoteUser> lsNoteUser = new ArrayList<>();
    private AdapterFriends User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_messeger_and_friends);
        setTitle("ListFriends");
        mappingview();
        loaddata();
        hiendsfriend();
        thongtinuser();
    }

    protected void mappingview() {

        toolbar = (Toolbar) findViewById(R.id.tbTile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        lsNoteUser = new ArrayList<>();
        listView = (ListView) findViewById(R.id.ls);
        User = new AdapterFriends(ListFriends.this, R.layout.adapter_list_friend, lsNoteUser);
        listView.setAdapter(User);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.title, menu);
        return super.onCreateOptionsMenu(menu);
    }
    protected void loaddata() {
        Intent intent = getIntent();
        myUid = intent.getStringExtra("myuid");
    }

    protected void hiendsfriend() {
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lsNoteUser.clear();
                User.notifyDataSetChanged();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (myUid.compareTo(data.getValue(NoteUser.class).getUid()) != 0)
                        lsNoteUser.add(data.getValue(NoteUser.class));
                    User.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    protected void thongtinuser() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListFriends.this, InformationFriends.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("NoteUser", lsNoteUser.get(i));
                bundle.putString("myuid", myUid);
                intent.putExtra("DataUser", bundle);
                startActivity(intent);
            }
        });
    }
}
