package com.example.socialmedia.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PostTest {
    private Post unit;
    private long id;
    private String content;
    private Date createdAt;

    @Before
    public void setUp() {
        unit = new Post();
    }

    @Test
    public void shouldGetAndSetFieldsCorrectly() {
        unit.setId(id);
        unit.setContent(content);
        unit.setCreatedAt(createdAt);

        assertThat(unit.getId()).isEqualTo(id);
        assertThat(unit.getContent()).isEqualTo(content);
        assertThat(unit.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    public void shouldSetTheFieldsFromTheConstructor() {
        User user = new User("firstName", "lastName", "email");
        Post post = new Post(user, content);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getUser()).isEqualTo(user);
        assertThat(post.getCreatedAt()).isEqualTo(createdAt);
    }
}
