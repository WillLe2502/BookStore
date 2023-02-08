package com.bookstore.admin.author;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bookstore.admin.entity.Author;


public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer> {
	@Query("SELECT a FROM Author a ORDER BY a.name ASC")
	public List<Author> findAll();
}
