package com.androidproj.chat;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import java.util.ArrayList;

/**
 * Created by NgocLong on 4/8/17.
 */


public class Main_Chat_Interface extends TabActivity{
    TabHost tabHost;
    String myUID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_interface);

        myUID = getIntent().getStringExtra("myuid");

        Log.i("Main_Chat", myUID);

        tabHost = getTabHost();

        TabSpec chatSpec = tabHost.newTabSpec("Chat");
        chatSpec.setIndicator("Chat", getResources().getDrawable(R.drawable.ic_add_black_24dp));
        Intent chatIntent = new Intent(this, ListMesseger.class);
        chatIntent.putExtra("myuid", myUID);
        chatSpec.setContent(chatIntent);

        // Tab for Songs
        TabSpec friendSpec = tabHost.newTabSpec("Friends");
        // setting Title and Icon for the Tab
        friendSpec.setIndicator("Friends", getResources().getDrawable(R.drawable.ic_add_black_24dp));
        Intent friendsIntent = new Intent(this, ListFriends.class);
        friendsIntent.putExtra("myuid", myUID);
        friendSpec.setContent(friendsIntent);

        // Tab for Videos
        TabSpec groupSpec = tabHost.newTabSpec("Groups");
        groupSpec.setIndicator("Groups", getResources().getDrawable(R.drawable.ic_add_black_24dp));
        Intent groupsIntent = new Intent(this, CreateGroupChat.class);
        Bundle b = new Bundle();
        b.putString("myuid", myUID);
        b.putString("name", "");
        b.putString("key", "0000000000000000000000000000000000000000");
        b.putStringArrayList("select", new ArrayList<String>());
        //Intent it = new Intent(ListMesseger.this, CreateGroupChat.class);
        groupsIntent.putExtra("cnn", b);
        groupSpec.setContent(groupsIntent);

        TabSpec userSpec = tabHost.newTabSpec("User");
        userSpec.setIndicator("User", getResources().getDrawable(R.drawable.ic_add_black_24dp));
        Intent userIntent = new Intent(this, EditUserInfo.class);
        userIntent.putExtra("myuid", myUID);
        userSpec.setContent(userIntent);

        tabHost.addTab(chatSpec);
        tabHost.addTab(friendSpec);
        tabHost.addTab(groupSpec);
        tabHost.addTab(userSpec);
    }
}
