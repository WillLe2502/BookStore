package com.bookstore.admin.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bookstore.admin.entity.Author;

public class AuthorService {
	@Autowired
	private AuthorRepository repo;

	public List<Author> listAllAuthor() {

		return repo.findAll();
	}
}
