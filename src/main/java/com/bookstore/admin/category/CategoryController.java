package com.bookstore.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bookstore.admin.entity.Category;

@Controller
public class CategoryController {

	@Autowired private CategoryService categoryService;

	@GetMapping("/categories")
	public String viewCategoryPage(Model model) {
		List<Category> listCategories = categoryService.listNoChildrenCategories();
		model.addAttribute("listCategories", listCategories);

		return "categories/categories";
	}
	
	
}
