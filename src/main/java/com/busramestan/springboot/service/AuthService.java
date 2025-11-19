package com.busramestan.springboot.service;


import com.busramestan.springboot.dto.auth.RegisterRequest;
import com.busramestan.springboot.dto.auth.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);

}
