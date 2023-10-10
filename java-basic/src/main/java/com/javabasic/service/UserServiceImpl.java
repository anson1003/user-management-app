package com.javabasic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.javabasic.exception.UserNotFoundException;
import com.javabasic.model.User;
import com.javabasic.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	 @Autowired
	    private UserRepository userRepository;

	    @Override
	    public ResponseEntity<List<User>> getAllUsers() {
	        List<User> users = userRepository.findAll();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    }

	    @Override
	    public ResponseEntity<User> getUserById(Long id) {
	        Optional<User> user = userRepository.findById(id);
	        if (user.isPresent()) {
	            return new ResponseEntity<>(user.get(), HttpStatus.OK);
	        } else {
	            throw new UserNotFoundException("User not found with id " + id);
	        }
	    }

	    @Override
	    public ResponseEntity<User> createUser(User user) {
	        User createdUser = userRepository.save(user);
	        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	    }

	    @Override
	    public ResponseEntity<User> updateUser(Long id, User userDetails) {
	        Optional<User> optionalUser = userRepository.findById(id);
	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();
	            user.setUsername(userDetails.getUsername());
	            user.setEmail(userDetails.getEmail());
	            user.setAge(userDetails.getAge());
	            userRepository.save(user);
	            return new ResponseEntity<>(user, HttpStatus.OK);
	        } else {
	            throw new UserNotFoundException("User not found with id " + id);
	        }
	    }

	    @Override
	    public ResponseEntity<Void> deleteUser(Long id) {
	        if (userRepository.existsById(id)) {
	            userRepository.deleteById(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } else {
	            throw new UserNotFoundException("User not found with id " + id);
	        }
	    }

}
