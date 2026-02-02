package com.bookly.service;

import java.util.List;

import com.bookly.model.OrderConfirm;

public interface OrderConfirmService 
{
	public OrderConfirm saveOrder(OrderConfirm orderconfirm);
	
	public List<OrderConfirm> getAllOrders();
}
