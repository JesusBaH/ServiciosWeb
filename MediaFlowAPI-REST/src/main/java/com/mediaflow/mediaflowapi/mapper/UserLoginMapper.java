package com.mediaflow.mediaflowapi.mapper;

import com.mediaflow.mediaflowapi.dto.UserLoginRequest;
import com.mediaflow.mediaflowapi.dto.UserLoginResponse;
import com.mediaflow.mediaflowapi.model.User;

public final class UserLoginMapper {

    public static UserLoginResponse toResponse(User user) {
        if (user == null) return null;

        return UserLoginResponse.builder()
                .email(user.getEmail())
                .build();
    }

    public static User toEntity(UserLoginRequest dto) {
        if (dto == null) return null;

        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public static void copyToEntity(UserLoginRequest dto, User entity) {
        if (dto == null || entity == null) return;
        
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
    }
}