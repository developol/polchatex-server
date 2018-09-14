package com.developol.polchatex.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDtoRepository extends CrudRepository<UserDto, Integer> {

    public List<UserDto> findAll();
    public UserDto getByUsername(String username);
}
