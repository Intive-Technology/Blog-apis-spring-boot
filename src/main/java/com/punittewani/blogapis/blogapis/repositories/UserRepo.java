package com.punittewani.blogapis.blogapis.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punittewani.blogapis.blogapis.entities.User;

public  interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
