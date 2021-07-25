package com.example.socialmedia.web.rest;

import com.example.socialmedia.domain.Post;
import com.example.socialmedia.exception.PostNotCreatedException;
import com.example.socialmedia.exception.UserNotFoundException;
import com.example.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/users/{userId}/posts", produces = "application/json")
    public ResponseEntity<Post> createPost(@PathVariable long userId, @RequestBody Post newPost) {
        Post post = postService.createPost(userId, newPost);
        if( null == post){
            throw new PostNotCreatedException("Post can not be created for user " + userId);
        }
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
}
