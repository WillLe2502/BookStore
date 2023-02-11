package com.bookstore.admin.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bookstore.admin.entity.Author;
import com.bookstore.admin.exception.AuthorNotFoundException;

@Service
public class AuthorService {
	public static final int AUTHORS_PER_PAGE = 20;
	
	@Autowired
	private AuthorRepository repo;

	public Author getAuthor(String name) throws AuthorNotFoundException {
		Author author = repo.findByName(name);
		if (author == null) {
			throw new AuthorNotFoundException("Could not find any author with alias " + name);
		}

		return author;
	}
	
	public List<Author> listAll() {
		return repo.findAll();
	}
	
	public Page<Author> listByPage(int pageNum, String sortField, String sortDir) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, AUTHORS_PER_PAGE, sort);

		return repo.findAll(pageable);		
	}
}