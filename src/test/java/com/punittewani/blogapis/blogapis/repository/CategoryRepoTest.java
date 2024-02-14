package com.punittewani.blogapis.blogapis.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
import com.punittewani.blogapis.blogapis.repositories.CategoryRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@Import(TestConfig.class)
public class CategoryRepoTest {
    @Autowired
    public CategoryRepo categoryRepo;

     @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setup(){
        this.categoryRepo.deleteAll();
        Categories categories = Categories.builder()
        .title("FIRST")
        .description("CATEGORY")
        .build();
        Categories categories2 = Categories.builder()
        .title("SECOND")
        .description("CATEGORY")
        .build();
        this.categoryRepo.save(categories);
        this.categoryRepo.save(categories2);
        this.categoryRepo.saveAll(List.of(categories,categories2));
    }

    @AfterEach
    @Transactional
    public void cleanup() {
        this.categoryRepo.deleteAll();
        entityManager.createNativeQuery("ALTER TABLE categories ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }
    @Test
    public void CategoryRepo_save_returnCategory(){

        Categories categories = Categories.builder()
        .title("test")
        .description("ets")
        .build();
        Categories savedCategory = this.categoryRepo.save(categories);
        Assertions.assertThat(savedCategory).isNotNull();
        Assertions.assertThat(savedCategory.getId()).isEqualTo(3);    
    }
    @Test
    public void CategoryRepo_findAll_returnsCategories(){

        List<Categories> categories = this.categoryRepo.findAll();
        Assertions.assertThat(categories).isNotNull();
        Assertions.assertThat(categories.size()).isEqualTo(2);
    }

    @Test
    public void CategoryRepo_findById_returnsCategory(){
        Categories categories = this.categoryRepo.findById(1).get();
        Assertions.assertThat(categories).isNotNull();
        Assertions.assertThat(categories.getId()).isEqualTo(1);
        Assertions.assertThat(categories.getTitle()).isEqualTo("FIRST");
    }

    @Test
    public void CategoryRepo_delete_returnsVoid(){
        Categories categories = this.categoryRepo.findById(1).get();
        this.categoryRepo.delete(categories);
        Assertions.assertThat(this.categoryRepo.findById(1)).isEmpty();
        List<Categories> categories2 = this.categoryRepo.findAll();
        Assertions.assertThat(categories2.size()).isEqualTo(1);
    }
}
