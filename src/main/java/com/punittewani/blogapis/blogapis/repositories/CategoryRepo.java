package com.punittewani.blogapis.blogapis.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.punittewani.blogapis.blogapis.entities.Categories;

public interface CategoryRepo extends JpaRepository<Categories,Integer> {
    
}
