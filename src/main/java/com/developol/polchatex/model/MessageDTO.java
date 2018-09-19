package com.developol.polchatex.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MessageDTO implements Serializable {
    private long id;
    private long ChatID;
    private String sender;
    private String content;
    private String createDateTime;
    private boolean isRead;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatID() {
        return ChatID;
    }

    public void setChatID(long chatID) {
        ChatID = chatID;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @JsonProperty(value="isSuccess")
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }


    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
