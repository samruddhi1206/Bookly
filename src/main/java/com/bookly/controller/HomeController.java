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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bookly.model.AddBook;
import com.bookly.model.UserDetail;
import com.bookly.service.AddBookService;
import com.bookly.service.CartService;
import com.bookly.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController
{
	@Autowired
	private AddBookService addbookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
    
	
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
		return "index";
	}
	@GetMapping("/signin")
	public String login()
	{
		return "login";
	}
	@GetMapping("/register")
	public String register()
	{
		return "register";
	}
	@GetMapping("/books")
	public String books(Model m,@RequestParam(value="category", defaultValue="")String category)
	{
		List<AddBook> books =addbookService.getAllBooks(category);
		m.addAttribute("book",books);
		m.addAttribute("paramValue", category);
		
		return "books";
		
		
	}
	@GetMapping("/book/{id}")
	public String book(@PathVariable int id,Model m)
	{
		AddBook bookById =  addbookService.getBookBYId(id);
		m.addAttribute("book",bookById);
		return "ViewBookDetails";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDetail user, @RequestParam("img") MultipartFile image, HttpSession session) throws IOException
	{
		String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
		user.setProfileImage(imageName);
		UserDetail saveUser = userService.saveUser(user);
		
		if(!ObjectUtils.isEmpty(saveUser))
		{
			if(!image.isEmpty()) 
			{
				File saveFile = new ClassPathResource("static/images").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile" + File.separator
						+ image.getOriginalFilename());

				
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			session.setAttribute("succMsg","Saved Successfully.");
		}
		else
		{
			session.setAttribute("errorMsg","Something wrong on Server.");
		}
		return "redirect:/register";

		
	}
}
