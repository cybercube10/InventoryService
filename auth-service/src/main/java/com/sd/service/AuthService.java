package com.sd.service;

import com.sd.dto.AuthRequest;
import com.sd.entity.UserCredential;
import com.sd.exceptions.EmailAlreadyExists;
import com.sd.repository.UserCredentialRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    public AuthService(UserCredentialRepository userCredentialRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userCredentialRepository = userCredentialRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String generateToken(String userName) {
        return jwtService.generateToken(userName);
    }

    public String saveUser(AuthRequest authRequest) {
        boolean userExists = userCredentialRepository.existsByEmail(authRequest.getEmail());
        if (userExists) {
            throw new EmailAlreadyExists("User with this email Already Exists");
        }
        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(authRequest.getName());
        userCredential.setStoreName(authRequest.getStoreName());
        userCredential.setEmail(authRequest.getEmail());
        userCredential.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        userCredentialRepository.save(userCredential);
        return "User added to the system.";
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
