package com.bookly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookly.model.OrderConfirm;

public interface OrderConfirmRepository extends JpaRepository<OrderConfirm, Integer>
{
	
}
