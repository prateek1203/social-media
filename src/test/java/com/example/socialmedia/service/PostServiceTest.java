package com.example.socialmedia.service;

import com.example.socialmedia.domain.Post;
import com.example.socialmedia.domain.User;
import com.example.socialmedia.exception.UserNotFoundException;
import com.example.socialmedia.repository.PostRepository;
import com.example.socialmedia.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {
    private PostService unit;

    @Mock
    PostRepository mockPostRepository;
    @Mock
    UserRepository mockUserRepository;
    @Mock
    Post mockPost;

    private long randomUserId;

    @Before
    public void setUp() {
        unit = new PostService(mockPostRepository, mockUserRepository);
    }

    @Test
    public void shouldDelegateCreatePostToPostRepository() {
        User user = new User("mockFirstName", "mockLatName", "mockEmail");
        Post post = new Post(user, "content");
        when(mockUserRepository.findById(randomUserId)).thenReturn(Optional.of(user));
        when(mockPostRepository.save(post)).thenReturn(mockPost);
        Post actualPost = unit.createPost(randomUserId, post);
        Assert.assertEquals(actualPost, mockPost);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldReturnNullForCreateUserIfUserDoesNotExist() throws UserNotFoundException {
        User user = new User("mockFirstName", "mockLatName", "mockEmail");
        Post post = new Post(user, "content");
        when(mockUserRepository.findById(randomUserId)).thenReturn(Optional.empty());
        Post actualPost = unit.createPost(randomUserId, post);
        Assert.assertNull(actualPost);
    }
}
