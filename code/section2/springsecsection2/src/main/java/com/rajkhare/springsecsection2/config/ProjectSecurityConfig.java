package com.rajkhare.springsecsection2.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

	/**
	 * Below is the custom security configurations
	 */

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(requests) -> requests.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards")
						.authenticated()
						.requestMatchers("/notices", "/contact").permitAll());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		
		return http.build();
	}

	/**
	 * Configuration to deny all requests
	 */

	/*
	 * @Bean SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
	 * throws Exception { http.authorizeHttpRequests((requests) ->
	 * requests.anyRequest().denyAll()); http.formLogin(withDefaults());
	 * http.httpBasic(withDefaults()); return http.build(); }
	 */

	/**
	 * Configuration to permit all requests
	 */

	/*
	 * @Bean SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
	 * throws Exception { http.authorizeHttpRequests((requests) ->
	 * requests.anyRequest().permitAll()); http.formLogin(withDefaults());
	 * http.httpBasic(withDefaults()); return http.build(); }
	 */
}
