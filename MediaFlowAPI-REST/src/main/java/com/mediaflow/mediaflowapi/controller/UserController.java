package com.mediaflow.mediaflowapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediaflow.mediaflowapi.dto.UserLoginRequest;
import com.mediaflow.mediaflowapi.dto.UserLoginResponse;
import com.mediaflow.mediaflowapi.dto.UserRequest;
import com.mediaflow.mediaflowapi.dto.UserResponse;
import com.mediaflow.mediaflowapi.mapper.UserLoginMapper;
import com.mediaflow.mediaflowapi.model.User;
import com.mediaflow.mediaflowapi.service.JwtService;
import com.mediaflow.mediaflowapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid user data provided")
    public UserResponse create(@Valid @RequestBody UserRequest user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.create(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Get all users with pagination")
    public List<UserResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        if (page < 0 || pageSize < 0) {
            throw new IllegalArgumentException("Pagination parameters cannot be negative.");
        }
        return userService.findAll(page, pageSize);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login a user and get JWT token")
    public ResponseEntity<UserLoginResponse> authenticate(@Valid @RequestBody UserLoginRequest loginRequest) {
        User authenticatedUser = userService.authenticate(loginRequest);
        UserLoginResponse userLoginResponse = UserLoginMapper.toResponse(authenticatedUser);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        userLoginResponse.setToken(jwtToken);
        userLoginResponse.setExpiresIn(jwtService.getExpirationTime());
        
        return ResponseEntity.ok(userLoginResponse);
    }
}