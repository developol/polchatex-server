package com.developol.polchatex.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    public List<User> findAll();
    public User getByUsername(String username);
    public int countAllByUsernameEquals(String username);
}
