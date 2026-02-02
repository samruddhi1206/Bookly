package com.bookly.service;



import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bookly.model.AddBook;

public interface AddBookService 
{
	public AddBook saveBook(AddBook addbook);
	
	public List<AddBook> getAllBooks(String category);
	
	public boolean deleteBook(Integer id);
	
	public AddBook getBookBYId(Integer id);
	
	public AddBook updateBook(AddBook addbook,MultipartFile file);
	
}
