package com.example.altenshop_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.altenshop_backend.LoginRequest;

@RestController
public class AuthController {
    //automatically isntanciate user service
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    //HTTP POST vers /account
    @PostMapping("/account")
    public ResponseEntity<String> register(@RequestBody User user) {

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email déjà utilisé");
        }
        userService.addUser(user);
        return ResponseEntity.ok("Utilisateur créé");
    }

   @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        var userOpt = userService.findByEmail(loginRequest.getEmail());
        if (userOpt.isPresent() && userService.checkPassword(userOpt.get(), loginRequest.getPassword())) {
            String token = jwtUtil.generateToken(userOpt.get().getEmail());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe invalide");
        }
    }
}

