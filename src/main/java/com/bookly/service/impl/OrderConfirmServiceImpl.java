package com.bookly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.bookly.model.AddBook;
import com.bookly.model.OrderConfirm;
import com.bookly.repository.OrderConfirmRepository;
import com.bookly.service.OrderConfirmService;

@Service
public class OrderConfirmServiceImpl implements OrderConfirmService
{
	@Autowired
	private OrderConfirmRepository orderConfirmRepository;

	@Override
	public OrderConfirm saveOrder(OrderConfirm orderconfirm) {
		
		return orderConfirmRepository.save(orderconfirm);
	}

	@Override
	public List<OrderConfirm> getAllOrders() {
		List<OrderConfirm> allOrder = orderConfirmRepository.findAll();
		/*if(ObjectUtils.isEmpty(id))
		{
			allOrder=orderConfirmRepository.findAll();
		}
		else
		{
			allOrder=orderConfirmRepository.findAllById(id);
		}
		*/
		
		return allOrder;
		
	}

}
