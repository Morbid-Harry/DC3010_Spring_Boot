package com.dc3010.DC3010_Spring_Boot.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * To hash passwords before being sent to the database must use a password encoder. 
 * Uses BCrypt library to encode and match passwords this is then used by Spring Security to automatically match passwords on login 
 * @author Harry
 *
 */
public class PasswordUtils implements PasswordEncoder{
	
	@Override
	public String encode(CharSequence rawPassword) {
		String salt = BCrypt.gensalt();
        return BCrypt.hashpw(rawPassword.toString(), salt);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
	}
	
}
