package com.punittewani.blogapis.blogapis.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class PostResponse {
    private List<PostsDto> content;
    private int page;
    private int pageSize;
    private long count;
    private int pages;
    private boolean lastpage;
    private List<CommentDto> comments;
}
