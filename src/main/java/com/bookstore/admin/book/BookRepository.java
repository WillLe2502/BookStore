package com.bookstore.admin.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bookstore.admin.entity.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
	@Query("SELECT b FROM Book b WHERE b.category.id = ?1 " + "OR b.category.allParentIDs LIKE %?2%")
	public Page<Book> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE b.name = ?1")
	public Book findByName(String name);
}
