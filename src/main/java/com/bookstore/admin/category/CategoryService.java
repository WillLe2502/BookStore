package com.bookstore.admin.category;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.entity.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository repo;

	public List<Category> listRootCategories() {
		return repo.findRootCategories();
	}

	public List<Category> getSubCategories(Integer Id) {

		return repo.findSubCategoriesById(Id);
	}
	
	public Category findIdByAlias (String alias) {
		return repo.findByAlias(alias);
	}
	

}
