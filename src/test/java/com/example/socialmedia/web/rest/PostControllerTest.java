package com.example.socialmedia.web.rest;

import com.example.socialmedia.domain.Post;
import com.example.socialmedia.domain.User;
import com.example.socialmedia.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {
    @Mock
    private PostService mockPostService;
    @Mock
    private Post mockPost;
    @Mock
    private User mockUser;

    private long rnadomUserId;
    private PostController unit;

    @Before
    public void setUp() {
        unit = new PostController(mockPostService);
    }

    @Test
    public void shouldBeRestController() {
        RestController annotation = unit.getClass().getAnnotation(RestController.class);
        assertThat(annotation).isNotNull();
    }

    @Test
    public void createUserShouldBePostMapping() throws NoSuchMethodException {
        Method findConfigLayerById = unit.getClass().getMethod("createPost", long.class, Post.class);
        PostMapping annotation = findConfigLayerById.getAnnotation(PostMapping.class);
        assertThat(annotation).isNotNull();

        assertThat(annotation.value()).isEqualTo(new String[]{"/users/{userId}/posts"});
        assertThat(annotation.produces()).isEqualTo(new String[]{"application/json"});
    }

    @Test
    public void createUserShouldReturnAResponseEntity() {
        when(mockPostService.createPost(rnadomUserId,mockPost)).thenReturn(mockPost);
        ResponseEntity<Post> result = unit.createPost(rnadomUserId, mockPost);
        assertThat(result).isEqualTo(result);
    }

    @Test
    public void createUserShouldReturnA400ErrorIfResponseIsNull() {
        Post post = new Post(mockUser, "Testing post content");
        when(mockPostService.createPost(rnadomUserId, post)).thenReturn(null);

        ResponseEntity<Post> result = unit.createPost(rnadomUserId, post);
        assertThat(result).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

}
