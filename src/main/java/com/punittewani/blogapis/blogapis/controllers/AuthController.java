package com.punittewani.blogapis.blogapis.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punittewani.blogapis.blogapis.entities.User;
import com.punittewani.blogapis.blogapis.payloads.LoginDto;
import com.punittewani.blogapis.blogapis.payloads.LoginResponse;
import com.punittewani.blogapis.blogapis.payloads.UserDto;
import com.punittewani.blogapis.blogapis.security.JwtHelper;
import com.punittewani.blogapis.blogapis.services.UserService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserDetailsService userDetailsService;

    private final AuthenticationManager manager;
    
    private final UserService userService;

    private final ModelMapper modelMapper;

    private final JwtHelper helper;
  @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        User user = this.modelMapper.map(userDetails, User.class);

        
        
        String token = this.helper.generateToken(userDetails);

        LoginResponse response = LoginResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername())
                .id(user.getId())
                .roles(user.getRoles())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            this.manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    } 
    @PostMapping("/register-normaluser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> registerNormalUser(@RequestBody UserDto user) {
        UserDto newUser = this.userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
