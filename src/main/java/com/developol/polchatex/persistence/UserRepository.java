package com.developol.polchatex.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

     List<User> findAll();
     User getByUsername(String username);
     User getById(long id);
}
