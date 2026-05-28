package com.jaringochi.service;

import com.jaringochi.domain.User;
import com.jaringochi.repository.UserRepository;

public class AuthService {
    private UserRepository userRepository = new UserRepository();

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean signup(String username, String password, String nickname) {
        if (userRepository.findByUsername(username) != null) {
            return false; // Already exists
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        userRepository.save(user);
        return true;
    }
}
