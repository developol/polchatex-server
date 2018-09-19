package com.developol.polchatex.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    public List<Message> findAllByChat_Id(long id);

    @Query("SELECT m FROM Message m " +
            "WHERE m.id = " +
            "(SELECT MAX(x.id) FROM Message x WHERE x.chat = :chat)")
    public Message getLastMessageByChat(@Param("chat")Chat chat);
}
