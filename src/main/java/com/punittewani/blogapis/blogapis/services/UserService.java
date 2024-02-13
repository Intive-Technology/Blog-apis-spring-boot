package com.punittewani.blogapis.blogapis.services;

import java.util.List;

import com.punittewani.blogapis.blogapis.payloads.UserDto;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);

}
