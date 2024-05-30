package com.example.eshop.service;

import com.example.eshop.TestSupport;
import com.example.eshop.dto.UserDto;
import com.example.eshop.dto.UserDtoConverter;
import com.example.eshop.exception.UserNotFoundException;
import com.example.eshop.model.User;
import com.example.eshop.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.*;
import static org.mockito.Mockito.*;

@TestInstance(Lifecycle.PER_CLASS)
class UserServiceTest extends TestSupport {

    private UserDtoConverter converter;
    private UserRepository repository;

    private UserService userService;

    @BeforeAll
    public void setUp() {
        converter = Mockito.mock(UserDtoConverter.class);
        repository = Mockito.mock(UserRepository.class);

        userService = new UserService(repository, converter);
    }

    @Test
    public void testGetAllUsers_itShouldReturnUserDtoList() {
        List<User> userList = generateUsers();
        List<UserDto> userDtoList = generateUserDtoList(userList);

        when(repository.findAll()).thenReturn(userList);
        when(converter.convert(userList)).thenReturn(userDtoList);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(userDtoList, result);
        verify(repository).findAll();
        verify(converter).convert(userList);
    }

    @Test
    public void testGetUserByEmail_whenUserEmailExist_itShouldReturnUserDto() {
        String email = "test@test.com";
        User user = generateUser(email);
        UserDto userDto = generateUserDto(email);

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        when(converter.convert(user)).thenReturn(userDto);

        UserDto result = userService.getUserByEmail(email);

        assertEquals(userDto, result);
        verify(repository).findByEmail(email);
        verify(converter).convert(user);
    }

    @Test
    public void testGetUserByEmail_whenUserEmailDoesNotExist_itShouldThrowUserNotFoundException() {
        String email = "test@test.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                userService.getUserByEmail(email)
        );

        verify(repository).findByEmail(email);
        verifyNoInteractions(converter);
    }


}