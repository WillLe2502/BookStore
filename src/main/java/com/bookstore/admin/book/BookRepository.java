package com.bookstore.admin.book;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import com.bookstore.admin.entity.book.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
	
	@Query("SELECT b FROM Book b WHERE b.enabled = true ORDER BY b.name ASC")
	public List<Book> findAllEnabled();

	@Query("SELECT b FROM Book b WHERE b.enabled = true "
			+ "AND (b.category.id = ?1 OR b.category.allParentIDs LIKE %?2%)"
			+ " ORDER BY b.name ASC")
	public Page<Book> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);
	
	public Book findByAlias(String alias);
	
	@Query(value = "SELECT * FROM books WHERE enabled = true AND "
			+ "MATCH(name, description) AGAINST (?1)", 
			nativeQuery = true)
	public Page<Book> search(String keyword, Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE b.enabled = true "
			+ "AND b.author.id = ?1"
			+ " ORDER BY b.name ASC")
	public Page<Book> listByAuthor(Integer authorId, String categoryIDMatch, Pageable pageable);
}
