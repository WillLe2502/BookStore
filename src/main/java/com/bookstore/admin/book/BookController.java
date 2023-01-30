package com.bookstore.admin.book;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookstore.admin.category.CategoryService;
import com.bookstore.admin.entity.Book;
import com.bookstore.admin.entity.Category;
import com.bookstore.admin.exception.BookNotFoundException;
import com.bookstore.admin.exception.CategoryNotFoundException;

@Controller
public class BookController {
	@Autowired private BookService bookService;
	@Autowired private CategoryService categoryService;
	
	@GetMapping("/categories/{category_alias}")
	public String viewCategoryFirstPage(
			@PathVariable("category_alias") String alias,
			Model model) {
		return viewCategoryByPage(alias, 1, model);
	}
	
	@GetMapping("/categories/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(
			@PathVariable("category_alias") String alias,
			@PathVariable("pageNum") int pageNum,
			Model model) {
		
		try {
			Category category = categoryService.getCategory(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);

			Page<Book> pageBooks = bookService.listByCategory(pageNum, category.getId());
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
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listBooks", listBooks);
			model.addAttribute("category", category);

			return "book/books_by_category";
		} 
		catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}
	
	@GetMapping("/books/{book_alias}")
	public String viewBookDetail(
			@PathVariable("book_alias") String alias, 
			Model model) {
		
		try {
			Book book = bookService.getBook(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(book.getCategory());
			
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("book", book);
			model.addAttribute("pageTitle", book.getName());
			
			return "book/book_detail";
		} catch (BookNotFoundException e) {
			return "error/404";
		}
	}
	
	@GetMapping("/search")
	public String searchFirstPage(@Param("keyword") String keyword, Model model) {
		return searchByPage(keyword, 1, model);
	}
	
	@GetMapping("/search/page/{pageNum}")
	public String searchByPage(@Param("keyword") String keyword,
			@PathVariable("pageNum") int pageNum,
			Model model) {
		Page<Book> pageBooks = bookService.search(keyword, pageNum);
		List<Book> listResult = pageBooks.getContent();

		long startCount = (pageNum - 1) * bookService.SEARCH_RESULTS_PER_PAGE + 1;
		long endCount = startCount + bookService.SEARCH_RESULTS_PER_PAGE - 1;
		if (endCount > pageBooks.getTotalElements()) {
			endCount = pageBooks.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageBooks.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageBooks.getTotalElements());
		model.addAttribute("pageTitle", keyword + " - Search Result");

		model.addAttribute("keyword", keyword);
		model.addAttribute("listResult", listResult);

		return "book/search_result";
	}
}
