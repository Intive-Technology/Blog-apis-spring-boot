package com.punittewani.blogapis.blogapis.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.punittewani.blogapis.blogapis.payloads.PostResponse;
import com.punittewani.blogapis.blogapis.payloads.PostsDto;
import com.punittewani.blogapis.blogapis.services.FileService;
import com.punittewani.blogapis.blogapis.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;


    private final  FileService fileService;

    @Value("${project.image}")
    private String path;


    /**
     * Creates a new post with the given postDto, userId, and categoryId.
     *
     * @param postDto    The DTO containing the details of the post.
     * @param userId     The ID of the user creating the post.
     * @param categoryId The ID of the category to which the post belongs.
     * @return The ResponseEntity containing the created post and the HTTP status
     *         code CREATED.
     */
    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostsDto> createPost(
            @Valid @RequestBody PostsDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId) {
        PostsDto post = postService.createPost(postDto, userId, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostsDto>> getPostByCategory(@PathVariable Integer categoryId) {
        {
            return ResponseEntity.ok(this.postService.getPostByCategory(categoryId));
        }

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostsDto>> getPostByUser(@PathVariable Integer userId) {
        {
            return ResponseEntity.ok(this.postService.getPostByUser(userId));
        }

    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostsDto>> searchByKeyword(@PathVariable String keyword) {
        List<PostsDto> postsDtos = this.postService.searchPosts(keyword);
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

@GetMapping(value = "/image/{fileName}",produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
public void downloadImage(@PathVariable String fileName, HttpServletResponse response) throws IOException {
    InputStream resource = this.fileService.getResource(path, fileName);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
     StreamUtils.copy(resource, response.getOutputStream());
}
@PostMapping("/image/upload/{postId}")
public ResponseEntity<PostsDto> imageUpload(@RequestParam("image") MultipartFile file, @PathVariable Integer postId) throws IOException {
    PostsDto post = postService.getPostById(postId);
    String fileName = fileService.uploadImage(path, file);
    System.out.println(fileName);
    post.setImage(fileName);
    return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(post, postId));
}
 
    /**
     * Get all posts with pagination and sorting options.
     *
     * @param page     The page number to retrieve. Default value is 0.
     * @param pageSize The number of posts per page. Default value is 5.
     * @param sortBy   The field to sort the posts by. Default value is "postId".
     * @param order    The order of the sorting. Default value is "asc".
     * @return The response entity containing the list of posts.
     */
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
            @RequestParam(value = "order", defaultValue = "asc", required = false) String order) {
        return ResponseEntity.ok(this.postService.getAllPost(page, pageSize, sortBy, order));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostsDto> getPostById(@PathVariable Integer postId) {
        {
            return ResponseEntity.ok(this.postService.getPostById(postId));
        }

    }

    

}