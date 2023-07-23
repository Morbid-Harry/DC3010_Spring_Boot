package com.dc3010.DC3010_Spring_Boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dc3010.DC3010_Spring_Boot.Service.UserService;

import jakarta.servlet.DispatcherType;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	UserService userService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.authorizeHttpRequests(authorize -> authorize.dispatcherTypeMatchers(DispatcherType.FORWARD,DispatcherType.ERROR, DispatcherType.ASYNC).permitAll().requestMatchers("/css/**", "/images/**", "/js/**","/resources/**").permitAll()
				.requestMatchers("/dashboard", "/profile", "/update/user/details", "/update/user/password", "/findOne/{id}","/show-interest/{usedId}/{projectId}", "/favourite/{projectId}", "/favourites", "/favourite/remove/{projectId}").hasAnyRole("USER","RM" ,"ADMIN").requestMatchers("/create", "/register").hasRole("ADMIN").requestMatchers("/manage","/create/project","/project/delete/{projectId}").hasAnyRole("ADMIN", "RM"))
		.formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/dashboard", true)
		.and().logout().logoutSuccessUrl("/login?logout").permitAll().and().userDetailsService(userService);
		return http.build();

	}
	
	
	/**
	 * 
	 * @return a Bycrypt password encoder that will be used by spring security to validate and encode new passwords
	 */
	@Bean
	PasswordEncoder getPasswordEncoder() {return new PasswordUtils();}
	
}
