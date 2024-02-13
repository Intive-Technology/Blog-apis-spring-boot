package com.punittewani.blogapis.blogapis.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.punittewani.blogapis.blogapis.entities.Categories;
import com.punittewani.blogapis.blogapis.entities.Posts;
import com.punittewani.blogapis.blogapis.entities.User;
import com.punittewani.blogapis.blogapis.exceptions.ResourceNotFoundException;
import com.punittewani.blogapis.blogapis.payloads.PostResponse;
import com.punittewani.blogapis.blogapis.payloads.PostsDto;
import com.punittewani.blogapis.blogapis.repositories.CategoryRepo;
import com.punittewani.blogapis.blogapis.repositories.PostRepo;
import com.punittewani.blogapis.blogapis.repositories.UserRepo;
import com.punittewani.blogapis.blogapis.services.PostService;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired 
    private CategoryRepo categoryRepo;
    @Override
    public PostsDto createPost(PostsDto postDto,Integer userId,Integer categoryId) {
        Posts newPost = this.modelMapper.map(postDto, Posts.class);
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        Categories category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
        newPost.setImage("default.png");
        newPost.setAddedDate(new Date());
        newPost.setUser(user);
        newPost.setCategory(category);
        Posts savedPost = this.postRepo.save(newPost);
        return this.modelMapper.map(savedPost,PostsDto.class);
    }

    @Override
    public PostsDto updatePost(PostsDto postsDto, Integer postId) {
        Posts post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
        post.setTitle(postsDto.getTitle());
        post.setContent(postsDto.getContent());
        post.setImage(postsDto.getImage());
        Posts savedPost =this.postRepo.save(post);
        return this.modelMapper.map(savedPost,PostsDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePost'");
    }

    @Override
    public PostsDto getPostById(Integer postId) {
        return this.modelMapper.map(this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId)),PostsDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer page , Integer pageSize,String sortBy , String order) {
        Sort sort = null;
        if(order.equalsIgnoreCase("desc")){
           sort= Sort.by(sortBy).descending();
        }else{
           sort= Sort.by(sortBy).ascending();
        }
        Pageable pg = PageRequest.of(page, pageSize,sort);
        Page<Posts> pagePosts = this.postRepo.findAll(pg);
        List<Posts> posts= pagePosts.getContent();
        List<PostsDto> postDtos = posts.stream().map(p->this.modelMapper.map(p,PostsDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPage(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setCount(pagePosts.getTotalElements());
        postResponse.setPages(pagePosts.getTotalPages());
        postResponse.setLastpage(pagePosts.isLast());
        return postResponse;

    }

    @Override
    public List<PostsDto> getPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        return this.postRepo.findByUser(user).stream().map(p->this.modelMapper.map(p,PostsDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostsDto> getPostByCategory(Integer categoryId) {
        Categories category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
        return this.postRepo.findByCategory(category).stream().map(p->this.modelMapper.map(p,PostsDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostsDto> searchPosts(String keyword) {
        List<Posts> posts = this.postRepo.findByTitleContaining(keyword);
        List<PostsDto> postDtos = posts.stream().map(p->this.modelMapper.map(p,PostsDto.class)).collect(Collectors.toList());
        return postDtos;
    }
    
}
