package com.androidproj.chat;

import java.io.Serializable;

/**
 * Created by Nguyen Ba Thai on 3/3/2017.
 */

public class NoteUser implements Serializable {
    private String email,password,uid,username;
    private Boolean active,firstlogin;

    public NoteUser() {
    }

    public NoteUser(Boolean active, String email, Boolean firstlogin, String password, String uid, String username) {
        this.active = active;
        this.email = email;
        this.firstlogin = firstlogin;
        this.password = password;
        this.uid = uid;
        this.username = username;
    }

    public void setFirstlogin(Boolean firstlogin) {
        this.firstlogin = firstlogin;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}