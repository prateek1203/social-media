package com.example.socialmedia.web.rest;

import com.example.socialmedia.domain.Post;
import com.example.socialmedia.domain.User;
import com.example.socialmedia.exception.GlobalException;
import com.example.socialmedia.exception.NoNewsFeedException;
import com.example.socialmedia.exception.UserNotFoundException;
import com.example.socialmedia.service.PostService;
import com.example.socialmedia.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private UserService mockUserService;
    @Mock
    private PostService mockPostService;
    @Mock
    private User mockUser;

    private long randomId;
    private long followerId;
    private long followeeId;
    private UserController unit;

    @Before
    public void setUp() {
        unit = new UserController(mockUserService, mockPostService);
    }

    @Test
    public void shouldBeRestController() {
        RestController annotation = unit.getClass().getAnnotation(RestController.class);
        assertThat(annotation).isNotNull();
    }

    @Test
    public void createUserShouldBePostMapping() throws NoSuchMethodException {
        Method createUser = unit.getClass().getMethod("createUser", User.class);
        PostMapping annotation = createUser.getAnnotation(PostMapping.class);
        assertThat(annotation).isNotNull();

        assertThat(annotation.value()).isEqualTo(new String[]{});
        assertThat(annotation.produces()).isEqualTo(new String[]{"application/json"});
    }

    @Test
    public void createUserShouldReturnAResponseEntity() {
        when(mockUserService.createUser(mockUser)).thenReturn(mockUser);
        ResponseEntity<User> result = unit.createUser(mockUser);
        assertThat(result).isEqualTo(new ResponseEntity<>(mockUser, HttpStatus.CREATED));
    }

    @Test
    public void findUserByIdShouldBeGetMapping() throws NoSuchMethodException {
        Method findUserById = unit.getClass().getMethod("findUserById", long.class);
        GetMapping annotation = findUserById.getAnnotation(GetMapping.class);
        assertThat(annotation).isNotNull();

        assertThat(annotation.value()).isEqualTo(new String[]{"/{userId}"});
        assertThat(annotation.produces()).isEqualTo(new String[]{"application/json"});
    }

    @Test
    public void findUserByIdShouldReturnAResponseEntity() {
        when(mockUserService.findUserById(randomId)).thenReturn(mockUser);
        ResponseEntity<User> result = unit.findUserById(randomId);
        assertThat(result).isEqualTo(new ResponseEntity<>(mockUser, HttpStatus.OK));
    }

    @Test(expected = GlobalException.class)
    public void createUserShouldReturnA400ErrorIfResponseIsNull() throws GlobalException {
        User user = new User("mockFirstName", "mockLastName", "mockEmail");
        when(mockUserService.createUser(user)).thenReturn(null);
        ResponseEntity<User> result = unit.createUser(user);
        assertThat(result).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @Test(expected = UserNotFoundException.class)
    public void findUserByIdShouldReturnA400ErrorIfResponseIsNull() throws UserNotFoundException {
        Mockito.when(mockUserService.findUserById(randomId)).thenReturn(null);

        ResponseEntity<User> result = unit.findUserById(randomId);
        assertThat(result).isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Test
    public void getNewsFeedShouldBeGetMapping() throws NoSuchMethodException {
        Method getNewsFeed = unit.getClass().getMethod("getNewsFeed", long.class);
        GetMapping annotation = getNewsFeed.getAnnotation(GetMapping.class);
        assertThat(annotation).isNotNull();

        assertThat(annotation.value()).isEqualTo(new String[]{"/{userId}/feeds"});
        assertThat(annotation.produces()).isEqualTo(new String[]{"application/json"});
    }

    @Test
    public void getNewsFeedShouldReturnAResponseEntity() {
        Post post = new Post(mockUser, "Testing content");
        when(mockPostService.getNewsFeedForUser(randomId)).thenReturn(Collections.singletonList(post));
        ResponseEntity<List<Post>> result = unit.getNewsFeed(randomId);
        assertThat(result).isEqualTo(result);
    }

    @Test(expected = NoNewsFeedException.class)
    public void getNewsFeedShouldReturnA400ErrorIfResponseIsNull() throws NoNewsFeedException {
        when(mockPostService.getNewsFeedForUser(randomId)).thenReturn(null);

        ResponseEntity<List<Post>> result = unit.getNewsFeed(randomId);
        assertThat(result).isEqualTo(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Test
    public void followUserShouldBePatchMapping() throws NoSuchMethodException {
        Method followUser = unit.getClass().getMethod("followUser", long.class, long.class);
        PatchMapping annotation = followUser.getAnnotation(PatchMapping.class);
        assertThat(annotation).isNotNull();

        assertThat(annotation.value()).isEqualTo(new String[]{"/{followerId}/follows/{followeeId}"});
        assertThat(annotation.produces()).isEqualTo(new String[]{"application/json"});
    }

    @Test
    public void followUserShouldReturnAResponseEntity() {
        UserController mockController = mock(UserController.class);
        ResponseEntity<String> result = mockController.followUser(followerId, followeeId);
        assertThat(result).isEqualTo(result);
    }

    @Test
    public void unFollowUserShouldBePatchMapping() throws NoSuchMethodException {
        Method followUser = unit.getClass().getMethod("unFollowUser", long.class, long.class);
        PatchMapping annotation = followUser.getAnnotation(PatchMapping.class);
        assertThat(annotation).isNotNull();

        assertThat(annotation.value()).isEqualTo(new String[]{"/{followerId}/unfollows/{followeeId}"});
        assertThat(annotation.produces()).isEqualTo(new String[]{"application/json"});
    }

    @Test
    public void unFollowUserShouldReturnAResponseEntity() {
        UserController mockController = mock(UserController.class);
        ResponseEntity<String> result = mockController.unFollowUser(followerId, followeeId);
        assertThat(result).isEqualTo(result);
    }
}
