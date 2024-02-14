package com.punittewani.blogapis.blogapis.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaRepositories(basePackages = "com.punittewani.blogapis.blogapis.repositories")
@EntityScan(basePackages = "com.punittewani.blogapis.blogapis.entities")
@ComponentScan(basePackages = "com.punittewani.blogapis.blogap")
public class TestConfig {
    @Bean
    @Profile("test")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean 
    @Profile("test")
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    
}
