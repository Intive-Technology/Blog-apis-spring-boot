package com.punittewani.blogapis.blogapis.payloads;


import java.util.Set;

import com.punittewani.blogapis.blogapis.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginResponse {
    private String jwtToken;
    private String username;
    private Integer id;
    private Set<Role> roles;
}
