package com.punittewani.blogapis.blogapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punittewani.blogapis.blogapis.entities.Role;

public interface RoleRepo extends JpaRepository <Role,Integer> {
    
}
