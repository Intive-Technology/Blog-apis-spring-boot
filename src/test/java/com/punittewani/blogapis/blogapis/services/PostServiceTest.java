package com.punittewani.blogapis.blogapis.services;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

import com.punittewani.blogapis.blogapis.BlogapisApplication;
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
    Page<Posts> pagePost  = Mockito.mock(Page.class);
    Posts post = Posts.builder().title("test").content("date").build();
    PostsDto postsDto = PostsDto.builder().title("test").content("date").build();
    Mockito.when(modelMapperSpy.map(Mockito.any(PostsDto.class), Mockito.eq(Posts.class))).thenReturn(post);
    Mockito.when(modelMapperSpy.map(Mockito.any(Posts.class), Mockito.eq(PostsDto.class))).thenReturn(postsDto);
    Mockito.when(this.userRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
    Mockito.when(this.categoryRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(categories));
    Mockito.when(this.postRepo.save(Mockito.any(Posts.class))).thenReturn(post);
    Mockito.when(this.postRepo.findAll(Mockito.any(Pageable.class))).thenReturn(pagePost);
    postService.createPost(postsDto,1,1);

}

    @Test
    public void PostService_CreatePost_ReturnPostDto() {
        PostsDto postsDto = PostsDto.builder().title("test").content("date").build();
        PostsDto postDto = postService.createPost(postsDto,1,1);
        Assertions.assertThat(postDto).isNotNull();
    }

    @Test
    public void PostService_getAll_returnsPostDTO(){
        PostResponse posts = postService.getAllPost(0, 10, "title", "ASC");
        System.out.println("Post: "+ posts);
        System.out.println(posts.getContent());
        Assertions.assertThat(posts).isNotNull();  
    }
}
