package com.developol.polchatex.persistence;

import org.springframework.data.repository.CrudRepository;


public interface ChatRepository extends CrudRepository<Chat, Integer> {
    Chat getById(long id);
}
