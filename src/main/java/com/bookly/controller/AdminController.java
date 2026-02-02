package com.bookly.controller;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.bookly.model.AddBook;
import com.bookly.model.UserDetail;
import com.bookly.model.OrderConfirm;
import com.bookly.service.AddBookService;
import com.bookly.service.CartService;
import com.bookly.service.OrderConfirmService;
import com.bookly.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController
{
	@Autowired
	private AddBookService addbookservice;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderConfirmService orderService;
	
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
	
	@GetMapping("/")
	public String index()
	{
		return "admin/index";
	}
	@GetMapping("/addBook")
	public String addBook()
	{
		return "admin/addBook";
	}
	
	@PostMapping("/saveBook")
	public String saveBook(@ModelAttribute AddBook addbook,@RequestParam("file") MultipartFile image, HttpSession session ) throws IOException
	{
		
		String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
		addbook.setImage(imageName);
		
		AddBook saveBook = addbookservice.saveBook(addbook);
		
		if(!ObjectUtils.isEmpty(saveBook))
		{
			
			File saveFile = new ClassPathResource("static/images").getFile();

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "books" + File.separator
					+ image.getOriginalFilename());

			System.out.println(path);
			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			session.setAttribute("succMsg", "Book Added Successfully.");
		}
		else {
			session.setAttribute("errorMsg", "Something wrong on server.");
		}
		return "redirect:/admin/addBook";
		
	}
	@GetMapping("/viewbook")
	public String viewBook(Model m)
	{
		m.addAttribute("books", addbookservice.getAllBooks(null));
		return "admin/viewbook";
	}
	
	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable int id, HttpSession session)
	{
		boolean deleteBook = addbookservice.deleteBook(id);
		if(deleteBook)
		{
			session.setAttribute("succMsg", "Book delete successfully.");
		}
		else {
			session.setAttribute("errorMsg", "Something wrong on server.");
		}
		return "redirect:/admin/viewbook";
	}
	@GetMapping("/editbook/{id}")
	public String editBook( @PathVariable int id,Model m)
	{
		m.addAttribute("book",addbookservice.getBookBYId(id));
		return "admin/editbook";
	}
	@PostMapping("/updateBook")
	public String updateBook(@ModelAttribute AddBook addbook ,@RequestParam("file") MultipartFile image, HttpSession session, Model m)
	{
		AddBook updateBook = addbookservice.updateBook(addbook, image);
		if(!ObjectUtils.isEmpty(updateBook))
		{
			session.setAttribute("succMsg", "Book update successfully.");
		}
		else
		{
			session.setAttribute("errorMsg", "Something wrong on server.");
		}
		return "redirect:/admin/editbook/" + addbook.getId();
	}
	
	@GetMapping("/users")
	public String getAllUsers(Model m)
	{
		List<UserDetail> users = userService.getUsers("ROLE_USER");
		m.addAttribute("users",users);
		return "admin/users";
	}
	@GetMapping("/orders")
	public String getAllOrders(Model m)
	{
		m.addAttribute("orders",orderService.getAllOrders());
		return "admin/orders";
	}
	
}
