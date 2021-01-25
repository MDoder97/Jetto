package com.markododer.webproject.repositories;


import com.markododer.webproject.model.MyUser;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<MyUser, Long> {

    MyUser findByUsername(String username);
    boolean existsByUsername(String username);
}
