package com.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.user.model.User;
import com.user.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<List<User>> getAllUser() {
		List<User> user=userRepository.findAll();
		return ResponseEntity.ok().body(user);
	}

	@Override
	public ResponseEntity<User> getUserById(Long id) {
		Optional<User> user=userRepository.findById(id);
		if(user.isPresent()) {
		return ResponseEntity.ok().body(user.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<User> createUser(User user) {
		User createUser=userRepository.save(user);
		return ResponseEntity.ok().body(createUser);
	}

	@Override
	public ResponseEntity<User> deleteUser(Long id) {
		if(userRepository.existsById(id)) {
		userRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
	
	}

	@Override
	public ResponseEntity<User> updateUser(Long id, User userDetails) {
		Optional<User> optionalUser=userRepository.findById(id);
		if(optionalUser.isPresent()) {
			User user=optionalUser.get();
			user.setUsername(userDetails.getUsername());
			user.setMail(userDetails.getMail());
			user.setAge(userDetails.getAge());
			
			userRepository.save(user);
			return ResponseEntity.ok().body(user);
		}
		else {
		return ResponseEntity.notFound().build();
		}
	}
	
	

}
