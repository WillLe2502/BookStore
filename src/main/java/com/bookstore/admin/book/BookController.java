package com.bookstore.admin.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookstore.admin.category.CategoryService;
import com.bookstore.admin.entity.Book;
import com.bookstore.admin.entity.Category;
import com.bookstore.admin.exception.BookNotFoundException;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias, Model model) {
		return viewCategoryByPage(alias, 1, model);
	}

	@GetMapping("/categories/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias, @PathVariable("pageNum") int pageNum,
			Model model) {
		Category category = categoryService.getCategory(alias);
		if (category == null) {
			return "error/404";
		}

		List<Category> listCategoryParents = categoryService.getCategoryParents(category);

		Page<Book> pageBooks = bookService.listByCategory(pageNum, category.getId());
		List<Book> listBooks = pageBooks.getContent();

		long startCount = (pageNum - 1) * BookService.BOOKS_PER_PAGE + 1;
		long endCount = startCount + BookService.BOOKS_PER_PAGE - 1;
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

		return "books_by_category";
	}

	@GetMapping("/book/{book_name}")
	public String viewBookDetail(@PathVariable("book_name") String name, Model model) {
		try {
			Book book = bookService.getBook(name);
			List<Category> listCategoryParents = categoryService.getCategoryParents(book.getCategory());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("book", book);
			
			
			
			return "book_detail";
		} catch (BookNotFoundException e) {
			return "error/404";
		}
	}
}
