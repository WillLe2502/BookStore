package com.bookstore.admin.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookstore.admin.book.BookService;
import com.bookstore.admin.entity.Author;
import com.bookstore.admin.entity.book.Book;
import com.bookstore.admin.exception.AuthorNotFoundException;

@Controller
public class AuthorController {
	@Autowired private AuthorService authorService;
	@Autowired private BookService bookService;

	@GetMapping("/authors")
	public String listFirstPage (Model model) {
		return listByPage(1, model, "name", "asc");

	}
	
	@GetMapping("/authors/page/{pageNum}")
	public String listByPage(
			@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir
			) {
		Page<Author> page = authorService.listByPage(pageNum, sortField, sortDir);
		List<Author> listAuthors = page.getContent();

		long startCount = (pageNum - 1) * authorService.AUTHORS_PER_PAGE + 1;
		long endCount = startCount + authorService.AUTHORS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("listAuthors", listAuthors);
		model.addAttribute("moduleURL", "/authors");

		return "authors/authors";
	}
	
	@GetMapping("/authors/{author_name}")
	public String viewAuthorFirstPage(
			@PathVariable("author_name") String name,
			Model model) {
		return viewAuthorByPage(name, 1, model);
	}
	
	@GetMapping("/authors/{author_name}/page/{pageNum}")
	public String viewAuthorByPage(
			@PathVariable("author_name") String name,
			@PathVariable("pageNum") int pageNum,
			Model model) {

		try {
			Author author = authorService.getAuthor(name);
			
			Page<Book> pageBooks = bookService.listByAuthor(pageNum, author.getId());
			List<Book> listBooks = pageBooks.getContent();

			long startCount = (pageNum - 1) * bookService.BOOKS_PER_PAGE + 1;
			long endCount = startCount + bookService.BOOKS_PER_PAGE - 1;
			if (endCount > pageBooks.getTotalElements()) {
				endCount = pageBooks.getTotalElements();
			}


			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageBooks.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageBooks.getTotalElements());
			model.addAttribute("pageTitle", author.getName());
			model.addAttribute("listBooks", listBooks);
			model.addAttribute("author", author);

			return "book/books_by_author";
		} 
		catch (AuthorNotFoundException ex) {
			return "error/404";
		}
	}
}