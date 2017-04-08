package com.androidproj.chat;

import java.io.Serializable;

/**
 * Created by Nguyen Ba Thai on 31/3/2017.
 */

public class NoteConversation implements Serializable {
    private String nameConversation;
    private String conversationID;


    public NoteConversation() {
    }

    public NoteConversation(String nameConversation, String conversationID) {

        this.nameConversation = nameConversation;
        this.conversationID = conversationID;
;
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


}
