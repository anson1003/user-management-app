package com.javabasic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.javabasic.exception.UserNotFoundException;
import com.javabasic.model.User;
import com.javabasic.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
	
	@Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "user1", "user1@example.com", 30));
        userList.add(new User(2L, "user2", "user2@example.com", 35));
        
        when(userRepository.findAll()).thenReturn(userList);
        
        ResponseEntity<List<User>> response = userService.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetUserByIdFound() {
        User user = new User(1L, "user1", "user1@example.com", 30);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userService.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("user1", response.getBody().getUsername());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1L);
        });
    }
    
    @Test
    void testCreateUser() {
        User user = new User(null, "newuser", "newuser@example.com", 25);
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<User> response = userService.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newuser", response.getBody().getUsername());
    }

    @Test
    void testUpdateUserFound() {
        Long userId = 1L;
        User existingUser = new User(userId, "user1", "user1@example.com", 30);
        User updatedUser = new User(userId, "updateduser", "updateduser@example.com", 35);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        ResponseEntity<User> response = userService.updateUser(userId, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("updateduser", response.getBody().getUsername());
    }

    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        User updatedUser = new User(userId, "updateduser", "updateduser@example.com", 35);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(userId, updatedUser);
        });
    }

    @Test
    void testDeleteUserFound() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        ResponseEntity<Void> response = userService.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteUserNotFound() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(userId);
        });
    }

	
}
