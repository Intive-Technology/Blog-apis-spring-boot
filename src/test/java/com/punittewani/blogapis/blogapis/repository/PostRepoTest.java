package com.punittewani.blogapis.blogapis.repository;

import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.punittewani.blogapis.blogapis.configuration.TestConfig;
import com.punittewani.blogapis.blogapis.entities.Categories;
import com.punittewani.blogapis.blogapis.entities.Posts;
import com.punittewani.blogapis.blogapis.entities.Role;
import com.punittewani.blogapis.blogapis.entities.User;
import com.punittewani.blogapis.blogapis.repositories.CategoryRepo;
import com.punittewani.blogapis.blogapis.repositories.PostRepo;
import com.punittewani.blogapis.blogapis.repositories.UserRepo;

import jakarta.transaction.Transactional;


@Import(TestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class PostRepoTest {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    public void setup(){
        this.postRepo.deleteAll();
        this.userRepo.deleteAll();
        this.categoryRepo.deleteAll();
        Role  role2 = Role.builder().name("Normal").build();
        Categories categories = Categories.builder()
        .title("test")
        .description("ets")
        .build();
        Categories savedCategory = this.categoryRepo.save(categories);
        User user = User.builder()
                    .email("test1@gmail.com")
                    .name("Test Data")
                    .password("Punit@92655")
                    .about("Test About Data")
                    .roles(Set.of(role2))
                    .build();
        User savedUser = this.userRepo.save(user);
        Posts post = Posts.builder()
        .title("unique")
        .content("datet")
        .category(savedCategory)
        .user(savedUser)
        .build();

        postRepo.save(post);
    }

    @Test 
    @Transactional
    public void PostRepo_save_returnsPost(){
        Posts post = Posts.builder()
        .title("NOpe")
        .content("nope")
        .build();
        Posts savedPost = this.postRepo.save(post);
        Assertions.assertThat(savedPost).isNotNull();
        Assertions.assertThat(savedPost.getId()).isEqualTo(2);
        Assertions.assertThat(savedPost.getId()).isNotNull();
    }

    @Test
    public void PostRepo_findAll_returnsPosts(){
        List<Posts> posts = postRepo.findAll();
        Assertions.assertThat(posts.size()).isEqualTo(1);
    }

    @Test
    public void PostRepo_delete_returnPost(){
        this.postRepo.deleteAll();
        List<Posts> posts = postRepo.findAll();
        Assertions.assertThat(posts.size()).isEqualTo(0);
        
    }
}
