package com.markododer.webproject.services;

import com.markododer.webproject.dto.UserDto;
import com.markododer.webproject.dto.UserRegisterDto;
import com.markododer.webproject.repositories.UserRepository;
import com.markododer.webproject.model.MyUser;
import com.markododer.webproject.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IService<MyUser, Long> {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public MyUser save(MyUser user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<MyUser> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<MyUser> findAll() {
        return (List<MyUser>) userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public MyUser createUser(UserRegisterDto userRegisterDto) {
        MyUser user = new MyUser();
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        if(userRegisterDto.isAdmin())
            user.getAuthorities().add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        userRepository.save(user);
        return user;
    }

    public UserDto createDto(MyUser user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setAdmin(user.isAdmin());
        userDto.setUsername(user.getUsername());
        userDto.setReservations(user.getReservations());
        userDto.setJwt(jwtUtil.generateToken(user));
        return userDto;
    }
}
