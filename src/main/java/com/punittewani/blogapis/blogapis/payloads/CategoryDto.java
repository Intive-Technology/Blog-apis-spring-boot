package com.punittewani.blogapis.blogapis.payloads;

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
public class CategoryDto {
    private int id;
    @NotEmpty
    @Size(min=2,message = "Title must be minimum 2 characters")
    private String title;
    @NotEmpty
    @Size(min=2,message = "Description must be minimum 2 characters")
    private String description;
}
