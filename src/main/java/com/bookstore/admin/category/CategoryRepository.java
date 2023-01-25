package com.bookstore.admin.category;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
	
@Query("SELECT c FROM Category c WHERE c.enabled = true")	
public List<Category> findAllEnabled();




}
