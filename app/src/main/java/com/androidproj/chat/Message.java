package com.androidproj.chat;

/**
 * Created by NgocLong on 2/25/17.
 */

public class Message {
    private String msg, username, uid;

    public Message(){

    }

    public Message(String msg, String username, String uid) {
        this.msg = msg;
        this.username = username;
        this.uid = uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
