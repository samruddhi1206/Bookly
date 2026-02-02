package com.bookly.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bookly.model.UserDetail;

public interface UserService 
{
	public UserDetail saveUser(UserDetail user);
	
	public UserDetail getUserByEmail(String email);
	
	public List<UserDetail> getUsers(String role);
	
	
}


