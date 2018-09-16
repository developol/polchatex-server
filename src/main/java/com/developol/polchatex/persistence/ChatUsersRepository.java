package com.developol.polchatex.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatUsersRepository extends CrudRepository<ChatUsers, Integer> {

    @Query(value="SELECT u.username FROM ChatUsers c JOIN c.user u WHERE c.chat = :chat")
    List<String> findUsers(@Param("chat")Chat chat);
}
