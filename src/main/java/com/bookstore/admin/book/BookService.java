package com.bookstore.admin.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bookstore.admin.entity.Book;
import com.bookstore.admin.exception.BookNotFoundException;

@Service
public class BookService {
	public static final int BOOKS_PER_PAGE = 10;

	@Autowired private BookRepository repo;

	public Page<Book> listByCategory(int pageNum, Integer categoryId) {
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		Pageable pageable = PageRequest.of(pageNum - 1, BOOKS_PER_PAGE);

		return repo.listByCategory(categoryId, categoryIdMatch, pageable);

	}
	
	public Book getBook(String name) throws BookNotFoundException {
		Book book = repo.findByName(name);
		if (book == null) {
			throw new BookNotFoundException("Could not find any product with name " + name);
		}

		return book;
	}
	
	public List<Book> getAllBook(){
		List<Book> books = repo.findAllEnabled();
		return books;
	}
	}
	
