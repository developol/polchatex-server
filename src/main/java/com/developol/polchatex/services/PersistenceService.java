package com.developol.polchatex.services;


import com.developol.polchatex.model.WebSocketPayload;
import com.developol.polchatex.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceService {
    private MessageRepository messageRepository;
    private ChatRepository chatRepository;
    private UserRepository userRepository;
    private ChatUsersRepository chatUsersRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public PersistenceService(MessageRepository messageRepository,
                              ChatRepository chatRepository,
                              UserRepository userRepository,
                              ChatUsersRepository chatUsersRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatUsersRepository = chatUsersRepository;

        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public Message persistMessage(WebSocketPayload payload, String senderUsername, Chat chat) {
        Message message = new Message();
        message.setChat(chat);

        User sender = this.userRepository.getByUsername(senderUsername);
        if (sender == null) {
            return null;
        }

        message.setSender(sender);
        message.setContent(payload.getMessageContent());
        message.setRead(false); // read/unread mechanism isn't supported yet
        this.messageRepository.save(message);
        return message;
    }

    public Chat persistChat(String initiatorUsername, String[] usernames) {
        Chat chat = new Chat();
        chat.setSize(usernames.length + 1);
        this.chatRepository.save(chat);
        // by default chatName is empty - the frontend uses usernames provided in ChatDTO to build it
        //TODO (optional): fit the requesting user in the loop

        ChatUsers chatUsers = new ChatUsers();
        chatUsers.setChat(chat);
        chatUsers.setUser(this.getUser(initiatorUsername));
        this.chatUsersRepository.save(chatUsers);
        User user;

        for (String usrname : usernames) {
            chatUsers = new ChatUsers();
            user = this.getUser(usrname);
            chatUsers.setChat(chat);
            chatUsers.setUser(user);
            this.chatUsersRepository.save(chatUsers);
        }
        return chat;
    }

    public void persistUser(String username, String password) {
        User user = new User();

        //password encryption is done with bcrypt
        //this information is stored in the database using the { } notation
        //according to spring security's specification
        user.setPassword("{bcrypt}" + bCryptPasswordEncoder.encode(user.getPassword()));

        user.setEmail("place@holder.com"); // email functionalities not yet supported

        user.setUsername(username);
        this.userRepository.save(user);
    }

    public User getUser(String username) {
        return this.userRepository.getByUsername(username);
    }

    public Chat getChat(long chatID) {
        return this.chatRepository.getById(chatID);
    }

    public List<String> getChatUsers(Chat chat) {
        return this.chatUsersRepository.findUsers(chat);
    }

    public Iterable<Message> getChatHistory(long chatID) {
        return this.messageRepository.findAllByChat_Id(chatID);
    }

    public Iterable<Chat> getChatList(String username) { return this.chatUsersRepository.findChatList(username); }

    public Message getLastMessage(Chat chat) {
        return this.messageRepository.getLastMessageByChat(chat);
    }

    public boolean privateChatExists(String username1, String username2) {
        return this.chatUsersRepository.countofPrivateConversation(username1, username2) != 0;
    }

    public boolean isUserInChat(long chatID, String username) {
        return this.chatUsersRepository.isUserInChat(chatID, username) !=0;
    }

    public String getUsername(long id) {
        return this.userRepository.getById(id).getUsername();
    }
}
