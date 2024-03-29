package com.punittewani.blogapis.blogapis.services;


import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

import com.punittewani.blogapis.blogapis.configuration.TestConfig;
import com.punittewani.blogapis.blogapis.entities.Categories;
import com.punittewani.blogapis.blogapis.entities.Posts;
import com.punittewani.blogapis.blogapis.entities.User;
import com.punittewani.blogapis.blogapis.payloads.PostResponse;
import com.punittewani.blogapis.blogapis.payloads.PostsDto;
import com.punittewani.blogapis.blogapis.repositories.CategoryRepo;
import com.punittewani.blogapis.blogapis.repositories.PostRepo;
import com.punittewani.blogapis.blogapis.repositories.UserRepo;
import com.punittewani.blogapis.blogapis.services.impl.PostServiceImpl;

@ExtendWith(MockitoExtension.class)
@Import(TestConfig.class)
@ActiveProfiles("test")
public class PostServiceTest {
    @Mock
    private PostRepo postRepo;
   
    ModelMapper realModelMapper = new ModelMapper();

    @Mock
    private UserRepo userRepo;

    @Mock 
    private CategoryRepo categoryRepo;
    

    @Mock
    ModelMapper modelMapperSpy = Mockito.spy(realModelMapper);
    

    @InjectMocks 
    private PostServiceImpl postService;

    @BeforeEach
public void setup() {
    User user = User.builder().name("test").email("test@gmail.com").id(1).build();
    Categories categories = Categories.builder().title("trst").build();
    Posts post = Posts.builder().title("test").content("date").build();
    PostsDto postsDto = PostsDto.builder().title("test").content("date").build();
    Mockito.when(this.userRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
    Mockito.when(this.categoryRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(categories));
    Mockito.when(modelMapperSpy.map(Mockito.any(PostsDto.class), Mockito.eq(Posts.class))).thenReturn(post);
    postService.createPost(postsDto,1,1);

}

    @Test
    public void PostService_CreatePost_ReturnPostDto() {
        Posts post = Posts.builder().title("test").content("date").build();
        PostsDto postsDto = PostsDto.builder().title("test").content("date").build();
        Mockito.when(this.postRepo.save(Mockito.any(Posts.class))).thenReturn(post);
        Mockito.when(modelMapperSpy.map(Mockito.any(PostsDto.class), Mockito.eq(Posts.class))).thenReturn(post);
        Mockito.when(modelMapperSpy.map(Mockito.any(Posts.class), Mockito.eq(PostsDto.class))).thenReturn(postsDto);
        PostsDto postDto = postService.createPost(postsDto,1,1);
        Assertions.assertThat(postDto).isNotNull();
    }

    @Test
    public void PostService_getAll_returnsPostDTO(){
        Page<Posts> pagePost  = Mockito.mock(Page.class);
        Mockito.when(this.postRepo.findAll(Mockito.any(Pageable.class))).thenReturn(pagePost);
        PostResponse posts = postService.getAllPost(0, 10, "title", "ASC");
        System.out.println("Post: "+ posts);
        System.out.println(posts.getContent());
        Assertions.assertThat(posts).isNotNull();  
    }

    @Test
    public void PostService_findById_returnPost(){
        Posts post = Posts.builder().title("test").content("date").build();
        PostsDto postsDto = PostsDto.builder().title("test").content("date").build();
        Mockito.when(this.postRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(post));
        Mockito.when(modelMapperSpy.map(Mockito.any(Posts.class), Mockito.eq(PostsDto.class))).thenReturn(postsDto);
        PostsDto posts = postService.getPostById(1);
        System.out.println("Post: "+ posts);
        Assertions.assertThat(posts).isNotNull();
    }
}
