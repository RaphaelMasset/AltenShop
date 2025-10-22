package com.example.altenshop_backend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //add user with hashed password
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.add(user);
    }

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public UserService() {
        User admin = new User(1, "admin", "Admin", "admin@admin.com", "adminpass");
        addUser(admin);
    }
}
