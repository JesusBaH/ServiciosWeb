package com.mediaflow.MediaFlowAPI_GraphQL.service;

import com.mediaflow.MediaFlowAPI_GraphQL.model.User;
import com.mediaflow.MediaFlowAPI_GraphQL.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        Objects.requireNonNull(id);
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

   
}