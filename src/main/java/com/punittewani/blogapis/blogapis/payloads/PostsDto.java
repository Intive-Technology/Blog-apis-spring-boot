package com.punittewani.blogapis.blogapis.payloads;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsDto {
    private Integer id;
    @NotEmpty
    @Size(min = 2,message = "Title must be minimum 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "Content must be minimum 10 characters")
    private String content;
    private String image;
    private Date addedDate;
    private UserDto user;
    private CategoryDto category;
    private List<CommentDto> comments;
}
