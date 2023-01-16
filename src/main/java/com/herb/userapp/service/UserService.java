package com.herb.userapp.service;

import com.herb.userapp.dto.request.UserRequestDTO;
import com.herb.userapp.dto.response.UserResponseDTO;
import com.herb.userapp.entity.User;
import com.herb.userapp.error.CustomException;
import com.herb.userapp.mapper.UserMapper;
import com.herb.userapp.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger Logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepo repo;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO addUser(UserRequestDTO requestDTO) {
        if (repo.findByUsername(requestDTO.getUsername()).isEmpty()) {
            User user = mapper.toUser(requestDTO);
            user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
            repo.save(user);
            return mapper.toDto(user);
        } else {
            Logger.error("User already exists");
            throw new CustomException("User already exists");
        }
    }

    public UserResponseDTO getUserById(long id) {
        return mapper.toDto(repo
                .findById(id)
                .filter(User::getStatus)
                .orElseThrow(() -> new CustomException("User not found")));
    }

    public List<UserResponseDTO> getAllUsers() {
        return repo.findAll()
                .stream()
                .filter(User::getStatus)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDTO deleteUser(long id) {
        User user = repo.findById(id).orElseThrow(() -> new CustomException("User not found"));
        user.setStatus(false);
        return mapper.toDto(user);
    }
}
