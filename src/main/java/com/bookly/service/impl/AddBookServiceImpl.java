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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bookly.model.AddBook;
import com.bookly.repository.AddBookRepository;
import com.bookly.service.AddBookService;

@Service
public class AddBookServiceImpl implements AddBookService
{
	@Autowired
	private AddBookRepository bookRepository;
	@Override
	public AddBook saveBook(AddBook addbook) {
		
		return bookRepository.save(addbook);
	}
	@Override
	public List<AddBook> getAllBooks(String category) {
		List<AddBook> books =null;
		if(ObjectUtils.isEmpty(category))
		{
			books=bookRepository.findAll();
		}
		else
		{
			books=bookRepository.findByCategory(category);
		}
		
		return books;
	}
	@Override
	public boolean deleteBook(Integer id) {
		AddBook addbook = bookRepository.findById(id).orElse(null);
		if(!ObjectUtils.isEmpty(addbook))
		{
			bookRepository.delete(addbook);
			return true;
		}
		return false;
	}
	@Override
	public AddBook getBookBYId(Integer id) {
		AddBook addbook=bookRepository.findById(id).orElse(null);
		return addbook;
	}
	@Override
	public AddBook updateBook(AddBook addbook, MultipartFile file) {
		
		AddBook dbBook = getBookBYId(addbook.getId());
		System.out.println("ID"+ addbook.getId());
		if(dbBook == null)
		{
			throw new IllegalArgumentException("Book Not Found"+addbook.getId());
		}
		String imageName =(file == null || file.isEmpty())? dbBook.getImage(): file.getOriginalFilename();
		
		dbBook.setTitle(addbook.getTitle());
		dbBook.setAuthor(addbook.getAuthor());
		dbBook.setPrice(addbook.getPrice());
		dbBook.setCategory(addbook.getCategory());
		dbBook.setDescription(addbook.getDescription());
	
		dbBook.setImage(imageName);
		
		AddBook updateBook = bookRepository.save(dbBook);
		
		if(!ObjectUtils.isEmpty(updateBook))
		{
			if(!file.isEmpty())
			{
				
				try {
					File saveFile = new ClassPathResource("static/images").getFile();
					
					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "books" + File.separator
							+ file.getOriginalFilename());
					
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);


				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			return addbook;
		}
		return null;
	}
	
	

}
