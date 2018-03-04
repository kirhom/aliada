package com.aliada.aliadaback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// curl -X POST -vu android-aliada:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=password&username=jlong&grant_type=password&scope=write&client_secret=123456&client_id=android-aliada"
// curl -v POST http://127.0.0.1:8080/search -H "Authorization: Bearer <oauth_token>""

@SpringBootApplication
public class AliadaBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AliadaBackApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
