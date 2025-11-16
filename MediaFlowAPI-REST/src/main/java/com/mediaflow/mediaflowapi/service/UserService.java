package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.UserLoginRequest;
import com.mediaflow.mediaflowapi.dto.UserRequest;
import com.mediaflow.mediaflowapi.dto.UserResponse;
import com.mediaflow.mediaflowapi.model.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    List<UserResponse> findAll(int page, int pageSize);

    UserResponse findById(Long id);

    UserResponse create(UserRequest request);

    UserResponse update(Long id, UserRequest request);

    void delete(Long id);

    User authenticate(UserLoginRequest request);
}