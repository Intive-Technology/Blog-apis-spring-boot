package com.punittewani.blogapis.blogapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punittewani.blogapis.blogapis.config.AppConfig;
import com.punittewani.blogapis.blogapis.entities.Role;
import com.punittewani.blogapis.blogapis.entities.User;
import com.punittewani.blogapis.blogapis.exceptions.ResourceNotFoundException;
import com.punittewani.blogapis.blogapis.payloads.UserDto;
import com.punittewani.blogapis.blogapis.repositories.RoleRepo;
import com.punittewani.blogapis.blogapis.repositories.UserRepo;
import com.punittewani.blogapis.blogapis.services.UserService;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired 
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto user) {
        User userEntity = this.dtoToUser(user);
        Role normalRole = this.roleRepo.findById(AppConfig.NORMAL_ROLE_ID).orElseThrow(()->new ResourceNotFoundException("ROle", "ID", AppConfig.ADMIN_ROLE_ID));
        userEntity.getRoles().add(normalRole);
        User savedUser = this.userRepo.save(userEntity);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        // TODO Auto-generated method stub
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);

    }

    @Override
    public UserDto getUserById(Integer userId) {
         User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users =  this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    public User dtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }

}
