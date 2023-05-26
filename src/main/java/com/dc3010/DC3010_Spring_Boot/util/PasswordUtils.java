package com.dc3010.DC3010_Spring_Boot.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils implements PasswordEncoder{
	
	
	// Generate a salt and hash the password
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
