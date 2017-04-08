package com.androidproj.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.androidproj.chat.R.id.ls;

/**
 * Created by Nguyen Ba Thai on 8/3/2017.
 */

public class ListMesseger extends AppCompatActivity {
    public static int LOAD_CONVERSATION_LIST = 2;
    private Toolbar toolbar;
    private String myUid, k;
    private DatabaseReference databaseReference;
    private ArrayList<NoteConversation> lsNoteConversation = new ArrayList<NoteConversation>();
    private ListView listView;
    private AdapterMesseger Messeger;
    //private Button btnConversation, btnUsers, btnGroupChat, btnMyUser;
    //private TabHost mTabHost;
    private int udpPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_messeger_and_friends);
        setTitle("Messeger");
        loaddata();
        mappingview();
        timdsconversation();
        xemthongtinconversation();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                databaseReference.goOffline();
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
    protected void mappingview() {
        toolbar = (Toolbar) findViewById(R.id.tbTile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        listView = (ListView) findViewById(ls);


        /*btnConversation = (Button) findViewById(R.id.btnConversation);

        btnGroupChat = (Button) findViewById(btnGroupChat);

        btnMyUser = (Button) findViewById(btnMyUser);

        btnUsers = (Button) findViewById(btnUsers);*/

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Messeger = new AdapterMesseger(ListMesseger.this, R.layout.adapter_list_messeger, lsNoteConversation);

        listView.setAdapter(Messeger);

        /*btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("uid", myUid);
                Intent it = new Intent(ListMesseger.this, ListFriends.class);
                databaseReference.goOffline();
                it.putExtra("user", b);
                finish();
                startActivity(it);
            }
        });

        btnGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("myuid", myUid);
                b.putString("name", "");
                b.putString("key", "0000000000000000000000000000000000000000");
                b.putStringArrayList("select", new ArrayList<String>());
                Intent it = new Intent(ListMesseger.this, CreateGroupChat.class);
                it.putExtra("cnn", b);
                startActivity(it);
            }
        });

        btnMyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListMesseger.this, EditUserInfo.class);
                it.putExtra("myUid", myUid);
                Log.i("ListMess: ", myUid);
                startActivity(it);
            }
        });*/
        registerForContextMenu(listView);

    }

    protected void loaddata() {
        Intent intent = getIntent();
        myUid = intent.getStringExtra("myuid");
    }

    protected void timdsconversation()
    {
       // Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        lsNoteConversation.clear();
        Messeger.notifyDataSetChanged();
        databaseReference.child("Users").child(myUid).child("listMesseger").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String messId = dataSnapshot.getValue().toString();
                timnameconversation(messId);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String messId = dataSnapshot.getValue().toString();
                timnameconversation(messId);
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

    protected void timnameconversation(final String nhomchat) {
        databaseReference.child("Messeger").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (nhomchat.equals(dataSnapshot.child("conversationID").getValue().toString())) {
                    lsNoteConversation.add(dataSnapshot.getValue(NoteConversation.class));
                }
                Messeger.notifyDataSetChanged();
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

    protected void xemthongtinconversation() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListMesseger.this, Chat.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", lsNoteConversation.get(i).getConversationID());
                bundle.putString("myuid", myUid);
                bundle.putString("name", lsNoteConversation.get(i).getNameConversation());
                intent.putExtra("con", bundle);
                startActivityForResult(intent, 1);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                udpPosition = i;
                return false;
            }
        });
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuconversation, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
       switch (item.getItemId()) {

            case R.id.action_delete: {
                k = lsNoteConversation.get(udpPosition).getConversationID();
                databaseReference.child("Users").child(myUid).child("listMesseger").child(k).removeValue();
                databaseReference.child("Messeger").child(k).child("user").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (myUid.equals(dataSnapshot.getValue().toString())) {
                            databaseReference.child("Messeger").child(lsNoteConversation.get(udpPosition).getConversationID()).child("user").child(dataSnapshot.getKey().toString()).removeValue();
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
                databaseReference.child("Messeger").child(k).child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.getValue() == null) {
                                databaseReference.child("Messeger").child(k).setValue(null);
                                timdsconversation();
                            }
                        }catch (Exception e){
                            Toast.makeText(ListMesseger.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            return true;
            }
            default:
                return super.onContextItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == LOAD_CONVERSATION_LIST) {
            timdsconversation();
        }

        if (resultCode == CreateGroupChat.CREATEGROUPCHAT) {
            timdsconversation();
            Bundle b = data.getBundleExtra("con");
            Intent it = new Intent(ListMesseger.this, Chat.class);
            it.putExtra("con", b);
            startActivityForResult(it, 1);
        }

    }

}
