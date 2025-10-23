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
         System.out.println("Données reçues: " + user);
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                System.out.println("no email");
                return ResponseEntity.badRequest().body("Email obligatoire");
            }

            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                System.out.println("no pass");
                return ResponseEntity.badRequest().body("Mot de passe obligatoire");
            }

            if (userService.findByEmail(user.getEmail()).isPresent()) {
                System.out.println("email already used");
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }

            // Optionnel : valider les autres champs username, firstname
            System.out.println("try to add user");
            userService.addUser(user);
            return ResponseEntity.ok("Utilisateur créé");
        } catch (Exception e) {
            System.out.println("Erreur serveur: " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
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

