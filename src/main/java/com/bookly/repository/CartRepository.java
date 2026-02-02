package com.bookly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookly.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer>
{
	public Cart findByBookIdAndUserId(Integer bookId,Integer userId );
	
	public Integer countByUserId(Integer userId);
	
	public List<Cart> findByUserId (Integer userId);

}
