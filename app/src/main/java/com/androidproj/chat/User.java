package com.androidproj.chat;

import java.io.Serializable;

/**
 * Created by NgocLong on 2/25/17.
 */

public class User implements Serializable{
    private String uid, username, password, birthday, firstname, lastname, country, email, imgProfilePath;
    private boolean isFirstLogin, isActive;

    public User() {
    }

    public User(String uid, String username, String password, String birthday, String firstname, String lastname, String country, String email, boolean isFirstLogin, boolean isActive, String imgProfilePath) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.email = email;
        this.isFirstLogin = isFirstLogin;
        this.isActive = isActive;
        this.imgProfilePath = imgProfilePath;
    }

    public String getImgProfilePath() {
        return imgProfilePath;
    }

    public void setImgProfilePath(String imgProfilePath) {
        this.imgProfilePath = imgProfilePath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
