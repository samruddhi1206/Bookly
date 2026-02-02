package com.bookly.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookly.model.Cart;
import com.bookly.model.OrderConfirm;
import com.bookly.model.UserDetail;
import com.bookly.service.CartService;
import com.bookly.service.OrderConfirmService;
import com.bookly.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderConfirmService orderService;
	
	@GetMapping("/")
	public String home()
	{
		return "user/home";
		
	}
	@ModelAttribute
	public void getUserDetails(Principal p, Model m)
	{
		if (p != null) {
			String email = p.getName();
			UserDetail userDetail = userService.getUserByEmail(email);
			m.addAttribute("user", userDetail);
			Integer Countcart=cartService.getCountCart(userDetail.getId());
			m.addAttribute("CountCart",Countcart);
		}
	}
	@GetMapping("/addCart")
	public String addToCart(@RequestParam Integer bid,@RequestParam Integer uid,HttpSession session)
	{
		Cart saveCart=cartService.saveCart(bid, uid);
		if(ObjectUtils.isEmpty(saveCart))
		{
			session.setAttribute("errorMsg", "Book add to cart failed");
			System.out.println("Book add to cart failed");
		}
		else
		{
			session.setAttribute("succMsg", "Book add to cart succesfully");
			System.out.println("Book add successfully");
		}
		return "redirect:/book/"+ bid;
	}
	Double totalOrderPrice;
	@GetMapping("/carts")
	public String loadCartPage(Principal p,Model m)
	{
		UserDetail user=getLoggedInUserDetails(p);
		List<Cart> carts= cartService.getCartsByUser(user.getId());
		m.addAttribute("carts",carts);
		if (carts.size() > 0) {
			totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "user/carts";
		
	}
	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
		 cartService.updateQuantity(sy, cid);
		return "redirect:/user/carts";
	}
	
	
	
	private UserDetail getLoggedInUserDetails(Principal p)
	{
		String email=p.getName();
		UserDetail userDetail=userService.getUserByEmail(email);
		return userDetail;
	}
	
	@GetMapping("/orders")
	public String orderPage(Model m)
	{
		m.addAttribute("totalOrderPrice",totalOrderPrice);
		return "user/orders";
	}
	
	@GetMapping("/successorder")
	public String orderDone(@ModelAttribute OrderConfirm orderConfirm)
	{
		OrderConfirm saveOrder= orderService.saveOrder(orderConfirm);
		if(ObjectUtils.isEmpty(saveOrder))
		{
			
			System.out.println("Order add to cart failed");
		}
		else
		{
			
			System.out.println("Order add successfully");
		}
		return "user/successorder";
	}
}
