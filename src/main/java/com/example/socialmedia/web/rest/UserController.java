package com.example.socialmedia.web.rest;

import com.example.socialmedia.domain.Post;
import com.example.socialmedia.domain.User;
import com.example.socialmedia.exception.GlobalException;
import com.example.socialmedia.exception.NoNewsFeedException;
import com.example.socialmedia.exception.UserAlreadyExistException;
import com.example.socialmedia.exception.UserNotFoundException;
import com.example.socialmedia.service.PostService;
import com.example.socialmedia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody final User user) throws UserAlreadyExistException {
        LOGGER.info(">>>>>> Creating User >>>>>>>>");
        User newUser = userService.createUser(user);
        if (null == newUser) {
            throw new GlobalException("User can not be created with emailId: " + user.getEmail() );
        }
        LOGGER.debug("User created: " + newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<User> findUserById(@PathVariable long userId) {
        LOGGER.info(String.format("/users/%s", userId));
        User existingUser = userService.findUserById(userId);
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/feeds", produces = "application/json")
    public ResponseEntity<List<Post>> getNewsFeed(@PathVariable long userId) throws GlobalException {
        LOGGER.info(String.format("/users/%s/feed", userId));
        List<Post> posts = postService.getNewsFeedForUser(userId);
        if (null == posts) {
            throw new NoNewsFeedException("Error while finding feed for user " + userId);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PatchMapping(value = "/{followerId}/follows/{followeeId}", produces = "application/json")
    public ResponseEntity<String> followUser(@PathVariable long followerId, @PathVariable long followeeId) {
        LOGGER.info(String.format("/users/%s/follows/%s", followerId, followeeId));
        if (followerId != followeeId) {
            userService.followUser(followerId, followeeId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new GlobalException("User can not follow itself, Please send diff user ids");
        }

    }

    @PatchMapping(value = "/{followerId}/unfollows/{followeeId}", produces = "application/json")
    public ResponseEntity<String> unFollowUser(@PathVariable long followerId, @PathVariable long followeeId) {
        LOGGER.info(String.format("/users/%s/unfollows/%s", followerId, followerId));
        if (followerId != followeeId) {
            userService.unFollowUser(followerId, followeeId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new GlobalException("User can not unfollow itself, Please send diff user id");
        }
    }

    @GetMapping(value = "{userId}/followers", produces = "application/json")
    public ResponseEntity<List<User>> getFollowers(@PathVariable long userId) {
        List<User> users = userService.findFollowers(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
