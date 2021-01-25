package com.markododer.webproject.services;

import com.markododer.webproject.dto.UserDto;
import com.markododer.webproject.dto.UserRegisterDto;
import com.markododer.webproject.repositories.UserRepository;
import com.markododer.webproject.model.MyUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MyUser loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = userRepository.findByUsername(username);
        if(myUser == null) {
            throw new UsernameNotFoundException("User name "+username+" not found");
        }
        return myUser;
    }



    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}