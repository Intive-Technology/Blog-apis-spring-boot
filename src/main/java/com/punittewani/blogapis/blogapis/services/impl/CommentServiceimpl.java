package com.punittewani.blogapis.blogapis.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punittewani.blogapis.blogapis.entities.Comments;
import com.punittewani.blogapis.blogapis.entities.Posts;
import com.punittewani.blogapis.blogapis.exceptions.ResourceNotFoundException;
import com.punittewani.blogapis.blogapis.payloads.CommentDto;
import com.punittewani.blogapis.blogapis.repositories.CommentsRepo;
import com.punittewani.blogapis.blogapis.repositories.PostRepo;
import com.punittewani.blogapis.blogapis.services.CommentService;
@Service
public class CommentServiceimpl implements CommentService {
    @Autowired
    private CommentsRepo commentsRepo;
    @Autowired 
    private ModelMapper modelMapper;

    @Autowired 
    private PostRepo postRepo;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Posts post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));        
        Comments comment = this.modelMapper.map(commentDto,Comments.class);
        comment.setPost(post);
        Comments savedComment =this.commentsRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
        

    }

    @Override
    public void deleteComment(Integer commentId) {
        Comments comment = this.commentsRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Id", commentId));
        this.commentsRepo.delete(comment);
    }
    
}
