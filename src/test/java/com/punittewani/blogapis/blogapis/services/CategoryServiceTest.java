package com.punittewani.blogapis.blogapis.services;

import org.junit.jupiter.api.Test;

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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

import com.punittewani.blogapis.blogapis.configuration.TestConfig;
import com.punittewani.blogapis.blogapis.entities.Categories;
import com.punittewani.blogapis.blogapis.payloads.CategoryDto;
import com.punittewani.blogapis.blogapis.repositories.CategoryRepo;
import com.punittewani.blogapis.blogapis.services.impl.CategoryServiceImpl;


@ExtendWith(MockitoExtension.class)
@Import(TestConfig.class)
@ActiveProfiles("test")
public class CategoryServiceTest {
    @Mock
    private CategoryRepo categoryRepo;

    ModelMapper realModelMapper = new ModelMapper();
    @Mock
    ModelMapper modelMapperSpy = Mockito.spy(realModelMapper);


    @InjectMocks
    private CategoryServiceImpl categoryService;

    // @BeforeEach
    // public  void setup(){
    //     Categories categories  = Categories.builder().title("test").build();
        
    // }

    @Test
    public void CategoryService_saveCategory_returnCategoryDto(){
     Categories categories  = Categories.builder().title("test").id(1).build();
     CategoryDto categoryDto = CategoryDto.builder().title("builder").id(1).build();
     when(modelMapperSpy.map(categoryDto, Categories.class)).thenReturn(categories);
     when(modelMapperSpy.map(categories, CategoryDto.class)).thenReturn(categoryDto);
     when(this.categoryRepo.save(ArgumentMatchers.any(Categories.class))).thenReturn(categories);
     CategoryDto savedCategory = this.categoryService.createCategory(categoryDto);
     Assertions.assertThat(savedCategory).isEqualTo(categoryDto);
    }

    @Test
    public void CategorySevice_findAll_returnCategoryDtoList(){
        Categories categories1 = Categories.builder().title("test").build();
        CategoryDto categoryDto1 = CategoryDto.builder().title("builder").id(1).build();
        List<CategoryDto> categoryDtoList = List.of(categoryDto1);
        when(this.categoryRepo.findAll()).thenReturn(List.of(categories1));
        when(modelMapperSpy.map(categories1, CategoryDto.class)).thenReturn(categoryDto1);
        List<CategoryDto> allCategory = this.categoryService.getAllCategories();
        Assertions.assertThat(allCategory).isEqualTo(categoryDtoList);
        Assertions.assertThat(allCategory.size()).isEqualTo(1);
    }

    @Test
    public void CategoryService_findById_returnCategoryDto(){
        Categories categories = Categories.builder().title("test").build();
        CategoryDto categoryDto = CategoryDto.builder().title("builder").id(1).build();
        when(this.categoryRepo.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(categories));
        when(modelMapperSpy.map(categories, CategoryDto.class)).thenReturn(categoryDto);
        CategoryDto foundCategory = this.categoryService.getCategoryById(1);
        Assertions.assertThat(foundCategory).isEqualTo(categoryDto);
    }
    
}
