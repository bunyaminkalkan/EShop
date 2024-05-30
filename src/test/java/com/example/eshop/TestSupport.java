package com.example.eshop;

import com.example.eshop.dto.UserDto;
import com.example.eshop.model.User;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestSupport {

    private static Long userId = 100L;

    public static List<User> generateUsers() {
        return IntStream.range(0, 5).mapToObj(i -> new User(
                    (long) i,
                    "firstName" + i,
                    "lastName" + i,
                    i + "@hilito.com",
                    "password" + i,
                    new Random(2).nextBoolean())
        ).collect(Collectors.toList());
    }

    public static List<UserDto> generateUserDtoList(List<User> userList) {
        return userList.stream()
                .map(from -> new UserDto(from.getFirstName() ,from.getLastName() ,from.getEmail()))
                .collect(Collectors.toList());
    }

    public static User generateUser(String email) {
        return new User(
                userId,
                "firstName" + userId,
                "lastName" + userId,
                userId + "@hilito.com",
                "password" + userId,
                true
        );
    }

    public static UserDto generateUserDto(String email) {
        return new UserDto(
                "firstName" + userId,
                "lastName" + userId,
                userId + "@hilito.com"
        );
    }
}
