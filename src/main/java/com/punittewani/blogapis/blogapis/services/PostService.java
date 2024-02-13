package com.punittewani.blogapis.blogapis.services;

import java.util.List;

import com.punittewani.blogapis.blogapis.payloads.PostResponse;
import com.punittewani.blogapis.blogapis.payloads.PostsDto;

public interface PostService {
    PostsDto createPost(PostsDto postDto,Integer userId,Integer categoryId);

    PostsDto updatePost(PostsDto postsDto, Integer postId);

    void deletePost(Integer postId);

    PostsDto getPostById(Integer postId);

    PostResponse getAllPost(Integer page , Integer pageSize,String sortBy ,String order);

    List<PostsDto> getPostByUser(Integer userId);

    List<PostsDto> getPostByCategory(Integer categoryId);

    List<PostsDto> searchPosts(String keyword);

}
