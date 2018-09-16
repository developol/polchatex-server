package com.developol.polchatex.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatUsersRepository extends CrudRepository<ChatUsers, Integer> {

    @Query(value="SELECT u.username FROM ChatUsers c JOIN c.user u WHERE c.chat = :chat")
    List<String> findUsers(@Param("chat")Chat chat);

    @Query(value="SELECT c.chat FROM ChatUsers c JOIN c.user u WHERE u.username = :username")
    List<Chat> findChatList(@Param("username") String username);

    @Query(value="SELECT COUNT(c) FROM ChatUsers c " +
            "WHERE c.user.username = :usr2 " +
            "AND c.chat " +
            "IN(SELECT x.chat FROM ChatUsers x WHERE x.chat.size = 2 AND x.user.username = :usr1)")
    int countofPrivateConversation(@Param("usr1") String username1,@Param("usr2") String username2);

    @Query(value="SELECT COUNT(c) FROM ChatUsers c WHERE c.chat.id = :id AND c.user.username = :username")
    int isUserInChat(@Param("id") long chatID, @Param("username") String username);
}
