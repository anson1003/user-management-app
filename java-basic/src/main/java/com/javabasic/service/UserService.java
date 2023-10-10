package com.javabasic.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.javabasic.model.User;

@Service
public interface UserService {

	ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<User> getUserById(Long id);
    ResponseEntity<User> createUser(User user);
    ResponseEntity<User> updateUser(Long id, User userDetails);
    ResponseEntity<Void> deleteUser(Long id);
}
