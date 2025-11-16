package com.mediaflow.mediaflowapi.mapper;

import com.mediaflow.mediaflowapi.dto.UserRequest;
import com.mediaflow.mediaflowapi.dto.UserResponse;
import com.mediaflow.mediaflowapi.model.User;

public final class UserMapper {

    public static UserResponse toResponse(User user) {
        if (user == null)
            return null;
        return UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .dateBirth(user.getDateBirth())
                .build();
    }

    public static User toEntity(UserRequest dto) {
        if (dto == null)
            return null;
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .dateBirth(dto.getDateBirth())
                .build();
    }

    public static void updateEntity(UserRequest dto, User entity) {
        if (dto == null || entity == null)
            return;
        
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setDateBirth(dto.getDateBirth());
    }
}