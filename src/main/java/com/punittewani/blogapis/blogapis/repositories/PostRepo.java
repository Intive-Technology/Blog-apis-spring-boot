package com.punittewani.blogapis.blogapis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punittewani.blogapis.blogapis.entities.Categories;
import com.punittewani.blogapis.blogapis.entities.Posts;
import com.punittewani.blogapis.blogapis.entities.User;

public interface PostRepo extends JpaRepository<Posts,Integer> {
    List<Posts> findByUser(User user);
    List<Posts> findByCategory(Categories category);
    List<Posts> findByTitleContaining(String keyword);
}
