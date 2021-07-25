package com.example.socialmedia.domain;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    private User unit;
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date createdAt;

    @Before
    public void setUp() {
        unit = new User();
    }

    @Test
    public void shouldGetAndSetFieldsCorrectly() {
        unit.setId(id);
        unit.setFirstName(firstName);
        unit.setLastName(lastName);
        unit.setEmail(email);
        unit.setCreatedAt(createdAt);

        assertThat(unit.getId()).isEqualTo(id);
        assertThat(unit.getFirstName()).isEqualTo(firstName);
        assertThat(unit.getLastName()).isEqualTo(lastName);
        assertThat(unit.getEmail()).isEqualTo(email);
        assertThat(unit.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    public void shouldSetTheFieldsFromTheConstructor() {
        User user = new User(firstName, lastName, email);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getEmail()).isEqualTo(email);
    }
}
