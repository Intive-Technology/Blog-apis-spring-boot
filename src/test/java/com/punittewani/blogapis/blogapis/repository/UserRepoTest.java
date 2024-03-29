package com.punittewani.blogapis.blogapis.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import com.punittewani.blogapis.blogapis.configuration.TestConfig;
import com.punittewani.blogapis.blogapis.entities.Role;
import com.punittewani.blogapis.blogapis.entities.User;
import com.punittewani.blogapis.blogapis.repositories.UserRepo;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import(TestConfig.class)
@ActiveProfiles("test")
public class UserRepoTest {
    @Autowired
    private UserRepo userRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @org.junit.jupiter.api.BeforeEach
    public void BeforeEach(){
        Set<Role> roles = new HashSet<>();
        Role role2 = Role.builder().name("Normal").build();
        roles.add(role2);

        User user = User.builder()

                    .email("test@gmail.com")
                    .name("Test Data")
                    .password("Punit@92655")
                    .about("Test About Data")
                    .roles(roles)
                    .build();
        User user2 = User.builder()
                    .email("test@gmail2.com")
                    .name("Test Data")
                    .password("Punit@92655")
                    .about("Test About Data")
                    .roles(roles)
                    .build();
        User user3 = User.builder()
                    .email("test@gmail3.com")
                    .name("Test Data")
                    .password("Punit@92655")
                    .about("Test About Data")
                    .roles(roles)
                    .build();

       userRepo.save(user);
       userRepo.save(user2);
       userRepo.save(user3);
    }
    @Test
    @Disabled
    public void UserRepo_save_ReturnSavedUser(){
        Role role2 = Role.builder().name("Normal").build();
        User user = User.builder()
                    .email("test1@gmail.com")
                    .name("Test Data")
                    .password("Punit@92655")
                    .about("Test About Data")
                    .roles(Set.of(role2))
                    .build();

        User savedUser = userRepo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        this.logger.info("Test data");        
        this.logger.info("Test data",user);        
        Assertions.assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    public void UserRepo_findByEmail_ReturnUser(){
        User user = userRepo.findByEmail("test@gmail.com").get();
        System.out.println("USER ID FROM THE TEST"+user.getId());
        Assertions.assertThat(user).isNotNull();
    }
    @Test
    public void UserRepo_findAll_ReturnAllUsers(){
        List<User> users = userRepo.findAll();
        Assertions.assertThat(users.size()).isEqualTo(3);
        Assertions.assertThat(users.size()).isNotEqualTo(2);
    }

    @Test
    public void UserRepo_delete(){
        User user = userRepo.findByEmail("test@gmail.com").get();
        Assertions.assertThat(user).isNotNull();
        userRepo.delete(user);
        assertThrows(NoSuchElementException.class,(()-> userRepo.findByEmail("test@gmail.com").get()));
    }

    @Test
    public void UserRepo_update(){
        User user = userRepo.findByEmail("test@gmail.com").get();
        Assertions.assertThat(user.getName()).isEqualTo("Test Data");
        Assertions.assertThat(user.getName()).isNotEqualTo("Punit");
        user.setName("Punit");
        userRepo.save(user);
        User updatedUser = userRepo.findByEmail("test@gmail.com").get();
        Assertions.assertThat(updatedUser.getName()).isEqualTo("Punit");
    }
}
