package com.example.socialmedia.service;

import com.example.socialmedia.domain.User;
import com.example.socialmedia.exception.GlobalException;
import com.example.socialmedia.exception.UserNotFoundException;
import com.example.socialmedia.repository.UserRepository;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        LOGGER.info("Creating user with details :- " + user);
        Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            LOGGER.debug(String.format("User already exists with email id %s ", user.getEmail()));
            return null;
        }
        return userRepository.saveAndFlush(user);
    }

    public User findUserById(@NotNull final long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (null != user) {
            user.getPosts().clear();
            user.getFollowing().clear();
        }
        return user;
    }

    public void followUser(long followerId, long followeeId) throws GlobalException  {
        User follower = userRepository.findById(followerId).orElse(null);
        User followee = userRepository.findById(followeeId).orElse(null);
        if (null != follower && null != followee){
            if (!follower.getFollowing().contains(followee)) {
                follower.getFollowing().add(followee);
                userRepository.saveAndFlush(follower);
            }else {
                throw new GlobalException("User already follows this user.");
            }
        } else {
            throw new UserNotFoundException("User unknown");
        }
    }

    public void unFollowUser(long followerId, long followeeId) throws GlobalException {
        User follower = userRepository.findById(followerId).orElse(null);
        User followee = userRepository.findById(followeeId).orElse(null);
        if (null != follower && null != followee){
            if (follower.getFollowing().contains(followee)) {
                follower.getFollowing().remove(followee);
                userRepository.saveAndFlush(follower);
            }else {
                throw new GlobalException("User does not follow this user.");
            }
        } else {
            throw new UserNotFoundException("User unknown");
        }
    }

    public List<User> findFollowers(long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        user.getPosts().clear();
        return user.getFollowing().stream().map(follower -> {
            follower.getFollowing().clear();
            follower.getPosts().clear();
            return user;
        }).distinct().collect(Collectors.toList());
    }
}
