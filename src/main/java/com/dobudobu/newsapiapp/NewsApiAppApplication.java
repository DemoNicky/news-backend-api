package com.dobudobu.newsapiapp;

import com.dobudobu.newsapiapp.Entity.Enum.Role;
import com.dobudobu.newsapiapp.Entity.User;
import com.dobudobu.newsapiapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
public class NewsApiAppApplication{

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(NewsApiAppApplication.class, args);
	}

//	public void run(String... args){
//		User adminAccount = userRepository.findByRole(Role.ADMIN);
//
//		if (null == adminAccount){
//			User user = new User();
//			user.setEmail("admin@gmail.com");
//			user.setFullname("admin");
//			user.setPhoneNumber("08123123123");
//			user.setUserCode("111111111");
//			user.setRole(Role.ADMIN);
//			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
//			userRepository.save(user);
//		}
//
//	}

}
