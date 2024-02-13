package com.punittewani.blogapis.blogapis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.punittewani.blogapis.blogapis.entities.User;
import com.punittewani.blogapis.blogapis.exceptions.ResourceNotFoundException;
import com.punittewani.blogapis.blogapis.repositories.UserRepo;
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Test");
         User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "Email :"  + username , 0));
         System.out.println(user);
         return user;
    }
    
}
