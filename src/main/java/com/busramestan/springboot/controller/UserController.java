package com.busramestan.springboot.controller;

import com.busramestan.springboot.dto.auth.RegisterRequest;
import com.busramestan.springboot.dto.auth.RegisterResponse;
import com.busramestan.springboot.dto.request.UserRequest;
import com.busramestan.springboot.dto.response.UserResponse;
import com.busramestan.springboot.service.AuthService;
import com.busramestan.springboot.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @Transactional
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Create user");
        return userService.createUser(userRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        RegisterResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        log.info("Get user by id: {}", id);
        return userService.getUserById(id);
    }
    @PutMapping("/{id}")
    @Transactional
    public UserResponse updateUser(@PathVariable Long id,
                                   @Valid @RequestBody UserRequest userRequest) {
        log.info("Update user id: {}", id);
        return userService.updateUser(id,userRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("Delete user by id: {}", id);
        userService.deleteUser(id);
    }

}
