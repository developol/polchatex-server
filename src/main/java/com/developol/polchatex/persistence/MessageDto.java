package com.developol.polchatex.persistence;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="messages")
public class MessageDto {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserDto sender;

    @Lob
    @Column(name="content", length=512)
    private String content;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatDto chat;

    @Column(name="isread")
    private boolean isRead;

    @Column(name="date")
    @CreationTimestamp
    private LocalDateTime createDateTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getSender() {
        return sender;
    }

    public void setSender(UserDto sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public ChatDto getChat() {
        return chat;
    }

    public void setChat(ChatDto chat) {
        this.chat = chat;
    }
}
