package com.busramestan.springboot.service.impl;

import com.busramestan.springboot.dto.auth.RegisterRequest;
import com.busramestan.springboot.dto.auth.RegisterResponse;
import com.busramestan.springboot.entity.User;
import com.busramestan.springboot.jwt.JwtService;
import com.busramestan.springboot.repository.UserRepository;
import com.busramestan.springboot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        User user = modelMapper.map(registerRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User savedUser = userRepository.save(user);
        String token =jwtService.generateToken(savedUser);
        return new RegisterResponse("User registered successfully", token);
    }
}
