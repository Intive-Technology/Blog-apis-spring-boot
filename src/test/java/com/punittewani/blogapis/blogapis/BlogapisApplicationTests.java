package com.punittewani.blogapis.blogapis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.punittewani.blogapis.blogapis.services.UserService;

@SpringBootTest
@ActiveProfiles("test")
class BlogapisApplicationTests {
	@Autowired
	private UserService userService;
	// @Test 
	// void testCreateUser() {
	// 	System.out.println("**********************");
	// 	String className = this.userService.getClass().getName();
	// 	String packageName = this.userService.getClass().getPackageName();
    // 	System.out.println(className);
	// 	System.out.println(packageName);
	// 	System.out.println("=======================");


	// }

	@Test
	public void contextLoad(){

	}

}
