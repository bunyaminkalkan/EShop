package com.example.eshop.controller;

import com.example.eshop.dto.CreateUserRequest;
import com.example.eshop.dto.UpdateUserRequest;
import com.example.eshop.dto.UserDto;
import com.example.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getUserById(email));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("email") String email, @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(email, request));
    }

    @PatchMapping("/{email}/deactive")
    public ResponseEntity<Void> deactivateUser(@PathVariable("email") String email) {
        userService.deactivateUser(email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{email}/active")
    public ResponseEntity<Void> activateUser(@PathVariable("email") String email) {
        userService.activateUser(email);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok().build();
    }
}
