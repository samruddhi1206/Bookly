package com.bookly.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bookly.model.AddBook;
import com.bookly.model.UserDetail;
import com.bookly.repository.UserRepository;
import com.bookly.service.UserService;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDetail saveUser(UserDetail user) {
		user.setRole("ROLE_USER");
		user.setIsEnable(true);
		String encodePassword= passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		UserDetail saveUser = userRepository.save(user);
		return saveUser;
	}


	@Override
	public UserDetail getUserByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}
    
	@Override
	public List<UserDetail> getUsers(String role)
	{
		return userRepository.findByRole(role);
		
	}
	/*
	@Override
	public UserDetail updateUser(UserDetail userDetail, MultipartFile file) {
	 UserDetail dbUser = getUserByEmail(userDetail.getEmail());
		
		if(dbUser == null)
		{
			throw new IllegalArgumentException("User Not Found"+userDetail.getEmail());
		}
		String imageName =(file == null || file.isEmpty())? dbUser.getProfileImage(): file.getOriginalFilename();
		
		dbUser.setName(userDetail.getName());
		dbUser.setMobile(userDetail.getMobile());
		
	
		dbUser.setProfileImage(imageName);
		
		UserDetail updateUser = userRepository.save(dbUser);
		
		if(!ObjectUtils.isEmpty(updateUser))
		{
			if(!file.isEmpty())
			{
				
				try {
					File saveFile = new ClassPathResource("static/images").getFile();
					
					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile" + File.separator
							+ file.getOriginalFilename());
					
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);


				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			return userDetail;
		}
	
		return null;
	}*/
}
