package com.punittewani.blogapis.blogapis.services;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.punittewani.blogapis.blogapis.configuration.TestConfig;
import com.punittewani.blogapis.blogapis.entities.Role;
import com.punittewani.blogapis.blogapis.entities.User;
import com.punittewani.blogapis.blogapis.payloads.UserDto;
import com.punittewani.blogapis.blogapis.repositories.RoleRepo;
import com.punittewani.blogapis.blogapis.repositories.UserRepo;
import com.punittewani.blogapis.blogapis.services.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@Import(TestConfig.class)
@ActiveProfiles("test")
public class UserServiceTest {
   

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RoleRepo roleRepo;
     
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void UserService_CreateUser_ReturnUserDto() {
        Role role2 = Role.builder().name("Normal").build();
        Set<Role> roles = new HashSet<>();
        User user = User.builder()
                .email("test@gmail.com")
                .name("Test Data")
                .password("Punit@92655")
                .about("Test About Data")
                .roles(roles)
                .build();
        UserDto userDto = UserDto.builder()
                .email("test@gmail.com")
                .password("Punit@92655")
                .about("Test")
                .build();
                System.out.println("user before mock: "+ user);
        when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
        when(userService.dtoToUser(userDto)).thenReturn(user);
        when(userService.userToDto(user)).thenReturn(userDto);
        // when(roleRepo.findById(1)).thenReturn(Optional.of(role1));
        when(roleRepo.findById(2)).thenReturn(Optional.of(role2));
        UserDto savedUser = this.userService.createUser(userDto);
        System.out.println("Saved User: "+ savedUser);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
    }
    @Test
    public void UserService_GetUser_getUser(){
        Role role2 = Role.builder().name("Normal").build();
        Set<Role> roles = new HashSet<>();
        User user = User.builder()
        .email("test1@gmail.com")
        .name("Test Data")
        .password("Punit@92655")
        .about("Test About Data")
        .roles(roles)
        .build();
        UserDto userDto = UserDto.builder()
                .email("test1@gmail.com")
                .about("Test")
                .build();
    when(userRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
    when(userService.userToDto(user)).thenReturn(userDto);
    UserDto getUserDto = this.userService.getUserById(1);
    Assertions.assertThat(getUserDto).isNotNull();
    Assertions.assertThat(userDto.getPassword()).isNull();
    Assertions.assertThat(getUserDto.getEmail()).isNotEqualTo("test@gmail.com");
    }

    @Test
    public void UserService_GetAll_returnListofDto(){
        Role role2 = Role.builder().name("Normal").build();
        UserDto userDto1 = UserDto.builder()
        .email("test1@gmail.com")
        .about("Test")
        .build();
        UserDto userDto2 = UserDto.builder()
        .email("test2@gmail.com")
        .about("Test")
        .build();
        Set<Role> roles = new HashSet<>();
        User user1 = User.builder()
        .email("test1@gmail.com")
        .name("Test Data")
        .password("Punit@92655")
        .about("Test About Data")
        .roles(roles)
        .build();
      
        User user2 = User.builder()
        .email("test2@gmail.com")
        .name("Test Data")
        .password("Punit@92655")
        .about("Test About Data")
        .roles(roles)
        .build();

        when(userRepo.findAll()).thenReturn(List.of(user1,user2));
        when(userService.userToDto(user1)).thenReturn(userDto1);
        when(userService.userToDto(user2)).thenReturn(userDto2);
        List<UserDto> userDtos = this.userService.getAllUsers();
        Assertions.assertThat(userDtos).isNotNull();
        Assertions.assertThat(userDtos.size()).isEqualTo(2);
        Assertions.assertThat(userDtos.get(0).getEmail()).isEqualTo("test1@gmail.com");
        Assertions.assertThat(userDtos.get(1).getEmail()).isEqualTo("test2@gmail.com");
        Assertions.assertThat(userDtos.get(1).getEmail()).isNotEqualTo("test1@gmail.com");
    }

}
