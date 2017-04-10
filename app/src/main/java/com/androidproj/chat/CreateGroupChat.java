package com.androidproj.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateGroupChat extends AppCompatActivity {

    private ListView viewSelect;
    private EditText edtNameGroup;
    private Toolbar toolbar;
    private AdapterUserForCreate adtSelect;
    private Button btnXoa;
    private ArrayList<String> lstSelect;
    private ArrayList<User> arrSelect;
    private DatabaseReference data;
    private String key, key1, myuid;
    private ImageButton btnAdd;
    public static int CREATEGROUPCHAT = 102, NOTCREATEGROUPCHAT = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_chat);
        getFormWidgets();
        addEventFromWidgets();
    }

    private void addEventFromWidgets() {

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = viewSelect.getChildCount() - 1; i >= 0; --i) {
                    View view = viewSelect.getChildAt(i);
                    CheckBox c = (CheckBox) view.findViewById(R.id.cbSelect);
                    if (c.isChecked()) {
                        try {
                            data.child("Users").child(lstSelect.get(i)).child("listMesseger").child(key).setValue(null);
                        } catch (Exception e) {

                        }
                        arrSelect.remove(i);
                        lstSelect.remove(i);
                    }
                }
                adtSelect.notifyDataSetChanged();
                data.child("Messeger").child(key).child("user").setValue(lstSelect);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CreateGroupChat.this, AddUserForGroup.class);
                Bundle b = new Bundle();
                b.putStringArrayList("lst", lstSelect);
                b.putString("myuid", myuid);
                if (key.length() > 22)
                    b.putString("key", key1);
                else b.putString("key", key);
                it.putExtra("ex", b);
                startActivityForResult(it, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AddUserForGroup.RESULTCODE) {
            lstSelect = data.getBundleExtra("ex").getStringArrayList("lst");
            arrSelect.clear();
            adtSelect.notifyDataSetChanged();
            getData();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnCreateGroup:
                if (lstSelect.size() < 3)
                    Toast.makeText(this, "Tối thiểu 3 thành viên.", Toast.LENGTH_SHORT).show();
                else if (edtNameGroup.length() == 0)
                    Toast.makeText(this, "Nhập Tên Group", Toast.LENGTH_SHORT).show();
                else create();
                return true;
            case android.R.id.home:
                if (key.equals("0000000000000000000000000000000000000000")) {
                    data.goOffline();
                    finish();
                    return true;
                } else if (edtNameGroup.length() == 0)
                    Toast.makeText(this, "Nhập Tên Group", Toast.LENGTH_SHORT).show();
                else nonCreate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void nonCreate() {
        Intent it = getIntent();
        Bundle b = getIntent().getBundleExtra("cnn");
        b.putString("name",edtNameGroup.getText().toString());
        it.putExtra("con",b );
        data.child("Messeger").child(key).child("nameConversation").setValue(edtNameGroup.getText().toString());
        data.child("Messeger").child(key).child("user").setValue(lstSelect);
        data.child("Messeger").child(key1).setValue(null);
        for (int i = 0; i < lstSelect.size(); ++i) {
            data.child("Users").child(lstSelect.get(i).toString()).child("listMesseger").child(key).setValue(key);
        }
        setResult(NOTCREATEGROUPCHAT, it);
        data.goOffline();
        finish();
    }

    private void create() {
        Conversation c = new Conversation(edtNameGroup.getText().toString(), key1, lstSelect, null);
        data.child("Messeger").child(key1).setValue(c);

        for (int i = 0; i < lstSelect.size(); ++i) {
            data.child("Users").child(lstSelect.get(i).toString()).child("listMesseger").child(key1).setValue(key1);
        }
        Intent it = getIntent();
        Bundle bun = new Bundle();
        bun.putString("name", edtNameGroup.getText().toString());
        bun.putString("myuid", getIntent().getBundleExtra("cnn").getString("myuid"));
        bun.putString("key", key1);
        it.putExtra("con", bun);
        setResult(CREATEGROUPCHAT, it);
        data.goOffline();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_create_group_chat, menu);
        if (key.length() < 22)
            menu.getItem(0).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    public void getFormWidgets() {
        Bundle b = getIntent().getBundleExtra("cnn");
        key = b.getString("key");
        myuid = b.getString("myuid");
        data = FirebaseDatabase.getInstance().getReference();
        key1 = data.child("Messeger").push().getKey();
        lstSelect = b.getStringArrayList("select");

        btnAdd = (ImageButton) findViewById(R.id.btnAddUser);
        viewSelect = (ListView) findViewById(R.id.lstvSelectUserInGroup);
        edtNameGroup = (EditText) findViewById(R.id.edtNameGroup);
        edtNameGroup.setText(b.getString("name"));
        toolbar = (Toolbar) findViewById(R.id.tbCreateGroup);
        setSupportActionBar(toolbar);
        if (!key.equals("0000000000000000000000000000000000000000")) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        arrSelect = new ArrayList<>();
        adtSelect = new AdapterUserForCreate(this, R.layout.adapter_user_for_create, arrSelect, myuid);
        btnXoa = (Button) findViewById(R.id.btnDeleteUser);
        viewSelect.setAdapter(adtSelect);
        getData();

    }


    public void getData() {
        {
            data.child("Users").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    User u = dataSnapshot.getValue(User.class);
                    if (find(u.getUid()) && !myuid.equals(u.getUid())) {
                        arrSelect.add(u);
                        adtSelect.notifyDataSetChanged();
                    }
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
    }

    private boolean find(String uid) {

        for (int i = 0; i < lstSelect.size(); ++i)
            if (uid.compareTo(lstSelect.get(i).toString()) == 0) return true;
        return false;
    }
}
