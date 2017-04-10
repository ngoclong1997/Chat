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
    private int udpPosition = -1;
    private boolean checkHas = false;
    private ArrayList<String> arr;
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

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Messeger = new AdapterMesseger(ListMesseger.this, R.layout.adapter_list_messeger, lsNoteConversation);

        listView.setAdapter(Messeger);

        registerForContextMenu(listView);

    }

    protected void loaddata() {
        Intent intent = getIntent();
        myUid = intent.getStringExtra("myuid");
    }

    protected void timdsconversation()
    {
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
                checkHas = false;
                k = lsNoteConversation.get(udpPosition).getConversationID();
                arr = new ArrayList<>();
                databaseReference.child("Users").child(myUid).child("listMesseger").child(k).removeValue();
                databaseReference.child("Messeger").child(k).child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (checkHas) return;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (myUid.compareTo(data.getValue().toString()) != 0)
                                arr.add(data.getValue().toString());
                        }
                        if (arr.size() == 0)
                            databaseReference.child("Messeger").child(k).removeValue();
                        else
                            databaseReference.child("Messeger").child(k).child("user").setValue(arr);
                        checkHas = true;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                timdsconversation();
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
