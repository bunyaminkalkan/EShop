package com.example.eshop.service;

import com.example.eshop.dto.CreateUserRequest;
import com.example.eshop.dto.UpdateUserRequest;
import com.example.eshop.dto.UserDto;
import com.example.eshop.dto.UserDtoConverter;
import com.example.eshop.exception.UserIsNotActiveException;
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

    public UserDto getUserById(String email) {
        User user = findUserByEmail(email);
        return userDtoConverter.convert(user);
    }

    public UserDto createUser(CreateUserRequest request) {
        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), true);
        return userDtoConverter.convert(userRepository.save(user));
    }

    public UserDto updateUser(String email, UpdateUserRequest request) {
        User user = findUserByEmail(email);
        if(!user.getIsActive()) {
            throw new UserIsNotActiveException();
        }
        User updatedUser = new User(
                user.getId(),
                request.getFirstName(),
                request.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
        return userDtoConverter.convert(userRepository.save(updatedUser));
    }

    public void deactivateUser(String email) {
        changeActiveStatus(email, false);
    }

    public void activateUser(String email) {
        changeActiveStatus(email, true);
    }

    public void deleteUser(String email) {
        if(!doesUserExist(email)) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        User user = findUserByEmail(email);
        userRepository.delete(user);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User couldn't be found by following email: " + email));
    }

    private void changeActiveStatus(String email, boolean active) {
        User user = findUserByEmail(email);
        User updatedUser = new User(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                active
        );
        userRepository.save(updatedUser);
    }

    private Boolean doesUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


}
