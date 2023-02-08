package com.bookstore.admin.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.entity.Author;
import com.bookstore.admin.exception.AuthorNotFoundException;

@Service
public class AuthorService {
	@Autowired private AuthorRepository repo;
	
	
	
	public Author getAuthor(String name) throws AuthorNotFoundException {
		Author author = repo.findByName(name);
		if (author == null) {
			throw new AuthorNotFoundException("Could not find any author with alias " + name);
		}

		return author;
	}
	

	public List<Author> listAllAuthor() {

		return repo.findAll();
	}
}
