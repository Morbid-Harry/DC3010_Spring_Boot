package com.dc3010.DC3010_Spring_Boot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dc3010.DC3010_Spring_Boot.Repository.UserRepository;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserRepository userRepo;
	
	public void addUser(User user)
	{
		userRepo.save(user);
	}
	
	public User getUserByLogin(String login)
	{
		return userRepo.findUserByLogin(login);
	}
	
	public User getUserByEmail(String email)
	{
		return userRepo.findUserByEmail(email);
	}
	
	
	public User getUserById(int id)
	{
		return userRepo.findUserByUserID(id);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = userRepo.findUserByLogin(username);
		
		if(user == null)
		{
	       throw new UsernameNotFoundException("Username not found " + username);
		}
		else
		{
			return new SecUserDetails(user);
		}
		
	}
	
	
	
}
