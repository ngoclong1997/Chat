package com.androidproj.chat;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pthhung on 28/02/2017.
 */
@IgnoreExtraProperties
public class Conversation implements Serializable {
    private String nameConversation;
    private String conversationID;
    private ArrayList<String> user;
    private ArrayList<Message> lstMesseger = new ArrayList<>();

    public ArrayList<String> getUser() {
        return user;
    }

    public void setUser(ArrayList<String> user) {
        this.user = user;
    }

    public Conversation(String nameConversation, String conversationID, ArrayList<String> user, ArrayList<Message> lstMesseger) {
        this.nameConversation = nameConversation;
        this.conversationID = conversationID;
        this.user = user;
        this.lstMesseger = lstMesseger;
    }

    public  Conversation(){

    }

    public String getNameConversation() {
        return nameConversation;
    }

    public void setNameConversation(String nameConversation) {
        this.nameConversation = nameConversation;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public ArrayList<Message> getLstMesseger() {
        return lstMesseger;
    }

    public void setLstMesseger(ArrayList<Message> lstMesseger) {
        this.lstMesseger = lstMesseger;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nameConversation", nameConversation);
        result.put("conversationID", conversationID);
        result.put("user", user);
        result.put("lstMesseger", lstMesseger);

        return result;
    }
}
