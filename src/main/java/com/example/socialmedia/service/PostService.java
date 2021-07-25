package com.example.socialmedia.service;

import com.example.socialmedia.domain.Post;
import com.example.socialmedia.domain.User;
import com.example.socialmedia.repository.PostRepository;
import com.example.socialmedia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Post createPost(long userId, final Post post) {
        LOGGER.info("creating post for user: " + userId);
        User user = userRepository.findById(userId).orElse(null);
        if (null != user){
            post.setUser(user);
            return postRepository.save(post);
        }
        return null;
    }

    public List<Post> getNewsFeedForUser(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (null != user){
            List<Long> ids = user.getFollowing().stream().map(User::getId).collect(Collectors.toList());
            List<Post> posts =  findAllPostsOfFollowings(ids);
            posts.addAll(user.getPosts()); // include own posts also
            posts.sort((o1, o2) -> - Long.compare(o1.getCreatedAt().getTime(), o2.getCreatedAt().getTime()));
            return posts;
        }
        return null;
    }

    private List<Post> findAllPostsOfFollowings(List<Long> ids) {
        LOGGER.debug("Finding posts for user ids: " + ids);
        return ids.stream()
                .map(postRepository::findAllByUserId)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
