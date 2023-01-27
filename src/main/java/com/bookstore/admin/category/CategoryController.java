package com.bookstore.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookstore.admin.entity.Category;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	public String viewCategory(Model model) {
		List<Category> listCategories = categoryService.listRootCategories();
		model.addAttribute("listCategories", listCategories);

		return "categories";
	}
	
	@GetMapping("/categories/{category_alias}")
	public String viewSubCategory(@PathVariable("category_alias") String alias, Model model){
		Category category = categoryService.findIdByAlias(alias);
		List<Category> listCategories = categoryService.getSubCategories(category.getId());
	
		model.addAttribute("listCategories", listCategories);
		
		
		return "categories";
	}
	
}
