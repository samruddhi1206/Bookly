package com.bookly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookly.model.UserDetail;


public interface UserRepository extends JpaRepository<UserDetail, Integer>
{
	public UserDetail findByEmail(String email);

	public List<UserDetail> findByRole(String role);
}
