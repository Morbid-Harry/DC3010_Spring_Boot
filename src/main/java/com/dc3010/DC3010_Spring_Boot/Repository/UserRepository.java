package com.dc3010.DC3010_Spring_Boot.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.dc3010.DC3010_Spring_Boot.beans.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	@EntityGraph(attributePaths = "favourtiedProjects")
	User findUserByLogin(String login);
	
	User findUserByEmail(String email);
	
	User findUserByUserID(int id);
	
}
