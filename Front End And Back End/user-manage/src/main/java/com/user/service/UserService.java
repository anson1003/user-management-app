package com.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.user.model.User;

@Service
public interface UserService {
	
	ResponseEntity<List<User>> getAllUser();
	ResponseEntity<User> getUserById(Long id);
	ResponseEntity<User> createUser(User user);
	ResponseEntity<User> deleteUser(Long id);
	ResponseEntity<User> updateUser(Long id, User userDetails);

}
