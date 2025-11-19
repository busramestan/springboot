package com.busramestan.springboot.service.impl;

import com.busramestan.springboot.dto.request.UserRequest;
import com.busramestan.springboot.dto.response.UserResponse;
import com.busramestan.springboot.entity.User;
import com.busramestan.springboot.excepiton.UserNotFoundException;
import com.busramestan.springboot.repository.UserRepository;
import com.busramestan.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        User createdUser = userRepository.save(user);
        log.info("User created");
        return modelMapper.map(createdUser,UserResponse.class);

    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);
        return userResponse;
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User updateUser = userRepository.findById(id).orElse(null);
        updateUser.setUsername(userRequest.getUsername());
        updateUser.setEmail(userRequest.getEmail());
        updateUser.setPassword(userRequest.getPassword());
        userRepository.save(updateUser);
        return modelMapper.map(updateUser,UserResponse.class);
    }

    @Override
    public void deleteUser(Long id) {
        User deleteUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(deleteUser);
    }

    @Override
    public List<UserResponse> getAllUsers(int page, int size) {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
        return userResponses;
    }
}
