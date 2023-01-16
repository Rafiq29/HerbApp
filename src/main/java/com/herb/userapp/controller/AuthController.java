package com.herb.userapp.controller;

import com.herb.userapp.dto.request.JwtRequest;
import com.herb.userapp.dto.request.UserRequestDTO;
import com.herb.userapp.dto.response.JwtResponse;
import com.herb.userapp.dto.response.UserResponseDTO;
import com.herb.userapp.service.AuthService;
import com.herb.userapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest request) {
        return authService.authenticate(request.getUsername(), request.getPassword());
    }

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO requestDTO) {
        return userService.addUser(requestDTO);
    }
}
