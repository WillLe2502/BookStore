package com.bookstore.admin.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bookstore.admin.entity.Author;





public class AuthorController {
	@Autowired private AuthorService authorService;
	@GetMapping("/Authors")
	public String viewAuthors (Model model) {

		List<Author> listAuthors = authorService.listAllAuthor();
		model.addAttribute("listAuthors", listAuthors);

		return "author/athors_list";

	}
}
