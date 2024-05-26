package com.example.eshop.service;

import com.example.eshop.dto.CreateUserRequest;
import com.example.eshop.dto.UpdateUserRequest;
import com.example.eshop.dto.UserDto;
import com.example.eshop.dto.UserDtoConverter;
import com.example.eshop.exception.UserNotFoundException;
import com.example.eshop.model.User;
import com.example.eshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userDtoConverter::convert).collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = findUserById(id);
        return userDtoConverter.convert(user);
    }

    public UserDto createUser(CreateUserRequest request) {
        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword());
        return userDtoConverter.convert(userRepository.save(user));
    }

    public UserDto updateUser(Long id, UpdateUserRequest request) {
        User user = findUserById(id);
        User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setFirstName(request.getFirstName());
        updatedUser.setLastName(request.getLastName());
        return userDtoConverter.convert(userRepository.save(updatedUser));
    }

    private User findUserById(Long id) {
       return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User couldn't be found by following id: " + id));
    }

    public void deactiveUser(Long id) {
       User user = findUserById(id);
       user.setActive(false);
       userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }
}
