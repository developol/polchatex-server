package com.developol.polchatex.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Integer> {
    public Chat getById(long id);

    @Query(value ="SELECT c FROM Chat c JOIN c.user1 u JOIN c.user2 d WHERE u.username = :username OR d.username = :username")
    List<Chat> findAllByUserName(@Param("username") String username);

    List<Chat> findAllByUser1_UsernameOrUser2_Username(String username, String usrname);

    int countAllByUser1EqualsAndUser2Equals(User user1, User user2);
}
