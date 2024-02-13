package com.punittewani.blogapis.blogapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punittewani.blogapis.blogapis.entities.Comments;

public interface CommentsRepo extends JpaRepository<Comments,Integer> {
}
