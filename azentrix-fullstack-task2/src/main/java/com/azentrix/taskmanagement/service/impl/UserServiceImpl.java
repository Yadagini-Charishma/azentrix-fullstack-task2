package com.azentrix.taskmanagement.service.impl;

import com.azentrix.taskmanagement.dto.RegisterRequestDTO;
import com.azentrix.taskmanagement.entity.User;
import com.azentrix.taskmanagement.repository.UserRepository;
import com.azentrix.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRequestDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already taken!");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // First user becomes ADMIN
        long userCount = userRepository.count();
        User.Role role = (userCount == 0) ? User.Role.ROLE_ADMIN : User.Role.ROLE_MEMBER;

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .active(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void toggleUserActive(Long id) {
        User user = findById(id);
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    @Override
    public void updateUserRole(Long id, User.Role role) {
        User user = findById(id);
        user.setRole(role);
        userRepository.save(user);
    }
}