package com.markododer.webproject.controller;


import com.markododer.webproject.dto.UserDto;
import com.markododer.webproject.dto.UserLoginDto;
import com.markododer.webproject.dto.UserRegisterDto;
import com.markododer.webproject.exceptions.UserExistsException;
import com.markododer.webproject.model.MyUser;
import com.markododer.webproject.services.MyUserDetailsService;
import com.markododer.webproject.services.UserService;
import com.markododer.webproject.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, UserService userService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody UserRegisterDto userRegisterDto) {
        if(userDetailsService.existsByUsername(userRegisterDto.getUsername()))
            throw new UserExistsException("User already exists");
        MyUser user = userService.createUser(userRegisterDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?>  login(@RequestBody UserLoginDto authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            MyUser user = userDetailsService.loadUserByUsername(authRequest.getUsername());
            UserDto userDto = userService.createDto(user);
            return ResponseEntity.ok(userDto);
        } catch (Exception e){
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

}
