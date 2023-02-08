package com.bookstore.admin.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bookstore.admin.entity.Author;




@Controller
public class AuthorController {
	@Autowired private AuthorService authorService;
	
	@GetMapping("/authors")
	public String viewAuthorsPage (Model model) {

		List<Author> authors = authorService.listAllAuthor();
		model.addAttribute("authors", authors);

		return "author/authors";

	}
}
