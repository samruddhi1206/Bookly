package com.bookly.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookly.model.AddBook;

public interface AddBookRepository extends JpaRepository<AddBook, Integer>{

	List<AddBook> findByCategory(String category);

	//List<AddBook> getAllBooks();
}
