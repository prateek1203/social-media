package com.example.socialmedia.service;

import com.example.socialmedia.domain.User;
import com.example.socialmedia.exception.GlobalException;
import com.example.socialmedia.exception.UserAlreadyExistException;
import com.example.socialmedia.exception.UserNotFoundException;
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
public class UserServiceTest {
    private UserService unit;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private User mockUser;

    private long randomUserId;
    private long randomFollowerId;
    private long randomFolloweeId;
    private String randomEmail;

    @Before
    public void setUp() {
        unit = new UserService(mockUserRepository);
    }

    @Test
    public void shouldDelegateCreateUserToUserRepository() {
        User user = new User("mockFirstName", "mockLatName", "mockEmail@mock.com");
        when(mockUserRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(mockUserRepository.saveAndFlush(user)).thenReturn(mockUser);
        User actualUser = unit.createUser(user);
        Assert.assertEquals(mockUser, actualUser);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void shouldNotCreateUserIfUserAlreadyExist() throws UserAlreadyExistException {
        User user = new User("mockFirstName", "mockLatName", "mockEmail");
        when(mockUserRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        User actualUser = unit.createUser(user);
        Assert.assertNull(actualUser);
    }

    @Test
    public void shouldDelegateFindUserByIdToUserRepository() {
        User user = new User("mockFirstName", "mockLatName", "mockEmail@mock.com");
        user.setId(1L);
        when(mockUserRepository.findById(user.getId())).thenReturn(Optional.of(mockUser));
        User actualUser = unit.findUserById(user.getId());
        Assert.assertEquals(mockUser, actualUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldDelegateNullIfUserDoesNotExist() throws UserNotFoundException {
        User user = new User("mockFirstName", "mockLatName", "mockEmail@mock.com");
        user.setId(1L);
        when(mockUserRepository.findById(user.getId())).thenReturn(Optional.empty());
        User actualUser = unit.findUserById(user.getId());
        Assert.assertNull(actualUser);
    }

    @Test
    public void shouldDelegateFollowUserToUserRepository() {
        when(mockUserRepository.findById(randomFollowerId)).thenReturn(Optional.of(mockUser));
        when(mockUserRepository.findById(randomFolloweeId)).thenReturn(Optional.of(mockUser));
        unit.followUser(randomFollowerId, randomFolloweeId);
        Assert.assertNotNull(mockUserRepository.findById(randomFollowerId).get().getFollowing());
    }

    @Test
    public void shouldNotFollowUserDoesNotExist() {
        when(mockUserRepository.findById(randomFollowerId)).thenReturn(Optional.empty());
        when(mockUserRepository.findById(randomFolloweeId)).thenReturn(Optional.of(mockUser));
        unit.followUser(randomFollowerId, randomFolloweeId);
        Assert.assertEquals(mockUserRepository.findById(randomFollowerId).get().getFollowing().size(), 0);
    }
}
