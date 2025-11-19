package com.busramestan.springboot.service;

import com.busramestan.springboot.dto.request.UserRequest;
import com.busramestan.springboot.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UserRequest userRequest);
    void deleteUser(Long id);
    List<UserResponse> getAllUsers(int page, int size);
}
