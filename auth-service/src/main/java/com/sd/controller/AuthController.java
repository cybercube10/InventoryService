package com.sd.controller;

import com.sd.dto.AuthRequest;
import com.sd.entity.UserCredential;
import com.sd.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final  AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@Valid @RequestBody  AuthRequest authRequest)
    {String response =  authService.saveUser(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
       Authentication authenticate =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()) {
            return authService.generateToken(authRequest.getEmail());
        }
        else throw new RuntimeException("Invalid username or password");
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        authService.validateToken(token);
        return "success";
    }
}
