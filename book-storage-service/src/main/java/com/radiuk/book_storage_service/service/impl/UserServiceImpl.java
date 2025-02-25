package com.radiuk.book_storage_service.service.impl;

import com.radiuk.book_storage_service.model.User;
import com.radiuk.book_storage_service.repository.UserRepository;
import com.radiuk.book_storage_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registration(User newUser) {
        String password = newUser.getPassword();
        newUser.setRole("USER");
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
    }
}
