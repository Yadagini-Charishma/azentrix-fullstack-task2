package com.azentrix.taskmanagement.service;

import com.azentrix.taskmanagement.dto.RegisterRequestDTO;
import com.azentrix.taskmanagement.entity.User;
import java.util.List;

public interface UserService {
    User register(RegisterRequestDTO dto);
    User findByUsername(String username);
    User findById(Long id);
    List<User> findAllUsers();
    void toggleUserActive(Long id);
    void updateUserRole(Long id, User.Role role);
}
