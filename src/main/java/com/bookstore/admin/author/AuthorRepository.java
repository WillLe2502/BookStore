package com.bookstore.admin.author;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bookstore.admin.entity.Author;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer> {
	@Query("SELECT a FROM Author a ORDER BY a.name ASC")
	public List<Author> findAll();
	
	@Query("SELECT a FROM Author a WHERE a.name = ?1")
	public Author findByName(String name);
	
	@Query("SELECT a FROM Author a ORDER BY a.name ASC")
	public Page<Author> findAll(Pageable pageable);
}