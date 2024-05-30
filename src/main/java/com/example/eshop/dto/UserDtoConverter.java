package com.example.eshop.dto;

import com.example.eshop.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    public UserDto convert(User from) {
        return new UserDto(from.getFirstName() ,from.getLastName() ,from.getEmail());
    }

    public List<UserDto> convert(List<User> userList) {
        return userList.stream().map(from -> new UserDto(from.getFirstName() ,from.getLastName() ,from.getEmail())).collect(Collectors.toList());
    }
}
