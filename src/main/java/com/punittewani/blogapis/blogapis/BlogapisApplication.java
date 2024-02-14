package com.punittewani.blogapis.blogapis;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.punittewani.blogapis.blogapis.config.AppConfig;
import com.punittewani.blogapis.blogapis.entities.Role;
import com.punittewani.blogapis.blogapis.repositories.RoleRepo;

@SpringBootApplication
public class BlogapisApplication implements CommandLineRunner  {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired 
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogapisApplication.class, args);
	}
	@Bean
	@Profile("production")
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Override
	public void run(String... args) throws Exception {
		Role admin = new Role();
		admin.setName("ROLE_ADMIN");
		admin.setId(AppConfig.ADMIN_ROLE_ID);
		Role normal = new Role();
		normal.setName("ROLE_NORMAL");
		normal.setId(AppConfig.NORMAL_ROLE_ID);
		List<Role> roles = List.of(admin,normal);
		this.roleRepo.saveAll(roles);
		System.out.println(this.passwordEncoder.encode("Punit@92655"));
	}

}
