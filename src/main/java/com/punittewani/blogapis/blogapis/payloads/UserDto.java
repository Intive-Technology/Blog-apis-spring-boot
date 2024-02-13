package com.punittewani.blogapis.blogapis.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter@Setter
@AllArgsConstructor
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 2,message = "Username must be minimum 2 characters")
    private String name;
    @Email(message = "Email address is not valid")
    private String email;
    @NotEmpty
    @Size(min = 10,message = "Password must be minimum 10 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private String password;
    @NotEmpty
    @Size(min = 10,message = "About must be minimum 10 characters")
    private String about;
}
