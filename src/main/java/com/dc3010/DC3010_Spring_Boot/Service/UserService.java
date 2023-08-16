package com.dc3010.DC3010_Spring_Boot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dc3010.DC3010_Spring_Boot.Repository.UserRepository;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;

/**
 * Handles all database CRUD operations for the User Table and Bean
 * @author Harry
 *
 */
@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserRepository userRepo;
	
	/**
	 * Saves a user to the user table or updates an exisitng user
	 * @param user the user object to either add or update to the db
	 */
	public void addUser(User user)
	{
		userRepo.save(user);
	}
	
	/**
	 * Gets the user object from the database that has the given username
	 * @param the given username of the desired user
	 */
	public User getUserByLogin(String login)
	{
		return userRepo.findUserByLogin(login);
	}
	
	/**
	 * Gets the user object from the database that has the given email
	 * @param the given email of the desired user
	 */
	public User getUserByEmail(String email)
	{
		return userRepo.findUserByEmail(email);
	}
	
	/**
	 * Gets the user object from the database that has the given id
	 * @param the given id of the desired user
	 */
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
