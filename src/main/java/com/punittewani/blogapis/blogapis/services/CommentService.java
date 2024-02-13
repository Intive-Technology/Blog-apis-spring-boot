package com.punittewani.blogapis.blogapis.services;

import com.punittewani.blogapis.blogapis.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);
}
