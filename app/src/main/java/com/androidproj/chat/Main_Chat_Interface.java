package com.androidproj.chat;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import java.util.ArrayList;

/**
 * Created by NgocLong on 4/8/17.
 */

@SuppressWarnings("deprecation")
public class Main_Chat_Interface extends TabActivity{
    TabHost tabHost;
    String myUID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_interface);

        myUID = getIntent().getStringExtra("myuid");

        tabHost = getTabHost();

        //tab for list messeger
        TabSpec chatSpec = tabHost.newTabSpec("Chat");
        chatSpec.setIndicator("Chat");
        Intent chatIntent = new Intent(this, ListMesseger.class);
        chatIntent.putExtra("myuid", myUID);
        chatSpec.setContent(chatIntent);

        // Tab for list friends
        TabSpec friendSpec = tabHost.newTabSpec("Friends");
        friendSpec.setIndicator("Friends");
        Intent friendsIntent = new Intent(this, ListFriends.class);
        friendsIntent.putExtra("myuid", myUID);
        friendSpec.setContent(friendsIntent);

        // Tab for create group chat
        TabSpec groupSpec = tabHost.newTabSpec("Groups");
        groupSpec.setIndicator("Groups");
        Intent groupsIntent = new Intent(this, CreateGroupChat.class);
        Bundle b = new Bundle();
        b.putString("myuid", myUID);
        b.putString("name", "");
        b.putString("key", "0000000000000000000000000000000000000000");
        b.putStringArrayList("select", new ArrayList<String>());
        groupsIntent.putExtra("cnn", b);
        groupSpec.setContent(groupsIntent);

        //tab for edit my user
        TabSpec userSpec = tabHost.newTabSpec("User");
        userSpec.setIndicator("User");
        Intent userIntent = new Intent(this, EditUserInfo.class);
        userIntent.putExtra("myuid", myUID);
        userSpec.setContent(userIntent);

        tabHost.addTab(chatSpec);
        tabHost.addTab(friendSpec);
        tabHost.addTab(groupSpec);
        tabHost.addTab(userSpec);
    }
}
