package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.UserLoginRequest;
import com.mediaflow.mediaflowapi.dto.UserRequest;
import com.mediaflow.mediaflowapi.dto.UserResponse;
import com.mediaflow.mediaflowapi.mapper.UserMapper;
import com.mediaflow.mediaflowapi.model.User;
import com.mediaflow.mediaflowapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        return userRepository.findAll(pageReq).stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        Objects.requireNonNull(id);
        User user = Objects.requireNonNull(
            userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id))
        );
        return Objects.requireNonNull(UserMapper.toResponse(user));
    }

    @Override
    @NonNull
    @Transactional
    public UserResponse create(UserRequest request) {
        Objects.requireNonNull(request);
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists.");
        }
        
        User user = Objects.requireNonNull(UserMapper.toEntity(request));
        
        User saved = userRepository.save(user);
        return Objects.requireNonNull(UserMapper.toResponse(saved));
    }

    @Override
    @NonNull
    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(request);
        
        User existing = Objects.requireNonNull(
            userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id))
        );
        
        UserMapper.updateEntity(request, existing);
        
        User saved = userRepository.save(existing);
        return Objects.requireNonNull(UserMapper.toResponse(saved));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id);
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User authenticate(UserLoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        return userRepository.findByEmail(request.getEmail())
                .orElseThrow();
    }
}