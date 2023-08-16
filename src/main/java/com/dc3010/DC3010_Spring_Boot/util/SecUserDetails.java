package com.dc3010.DC3010_Spring_Boot.util;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dc3010.DC3010_Spring_Boot.beans.User;

/**
 * When using a custom user entity and spring security you must override spring securities default UserDetails object with the custom implementation which is then applied in the SecurityConfig Class
 * When you login to the application as a user this object gets populated and stored in the session
 * @author Harry
 *
 */
public class SecUserDetails implements UserDetails {
	
	private User user;
	
	public SecUserDetails(User user)
	{
		this.user = user;
	}
	
	/**
	 * As roles are one string seperated by commas spring security requires them to be split up before using and then returned as a list
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(user.getRole().split(",")).map(SimpleGrantedAuthority::new).toList();
	}
	
	/**
	 * Get the password of the logged in user
	 */
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	/**
	 * Get the username of the logged in user
	 */
	@Override
	public String getUsername() {
		return user.getLogin();
	}
	
	/**
	 * Not needed
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/**
	 * Not needed
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
	 * Not needed
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Not needed
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	

}
