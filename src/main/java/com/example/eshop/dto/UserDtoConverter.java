package com.example.eshop.dto;

import com.example.eshop.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public UserDto convert(User from) {
        return new UserDto(from.getFirstName() ,from.getLastName() ,from.getEmail());
    }
}
