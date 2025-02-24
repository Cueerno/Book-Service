package com.radiuk.book_storage_service.service.impl;

import com.radiuk.book_storage_service.model.User;
import com.radiuk.book_storage_service.repository.UserRepository;
import com.radiuk.book_storage_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registration(User newUser) {
        User userToSave = new User();
        userToSave.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(userToSave);
    }
}
