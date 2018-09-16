package com.developol.polchatex.Model;

import com.developol.polchatex.persistence.Message;

public class ChatDTO {
    private long id;
    private String name;
    private String usernames[];
    private MessageDTO lastMessage;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public MessageDTO getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageDTO lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getUsernames() {
        return usernames;
    }

    public void setUsernames(String[] usernames) {
        this.usernames = usernames;
    }
}
