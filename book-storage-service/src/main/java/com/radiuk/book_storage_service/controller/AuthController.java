package com.radiuk.book_storage_service.controller;

import com.radiuk.book_storage_service.dto.AuthDTO;
import com.radiuk.book_storage_service.model.User;
import com.radiuk.book_storage_service.security.JwtCore;
import com.radiuk.book_storage_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final UserDetailsService userDetailsService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody AuthDTO authDTO) {

        userService.registration(convertToUser(authDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authDTO.getUsername());
        String token = jwtCore.generateToken(userDetails);
        System.out.println(token);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    public User convertToUser(AuthDTO authDTO) {
        return modelMapper.map(authDTO, User.class);
    }
}
