package com.herb.userapp.controller;

import com.herb.userapp.dto.response.UserResponseDTO;
import com.herb.userapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/get/{id}")
    public UserResponseDTO getById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/get/all")
    public List<UserResponseDTO> getAll() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/delete/{id}")
    public UserResponseDTO delete(@PathVariable("id") long id) {
        return userService.deleteUser(id);
    }
}
