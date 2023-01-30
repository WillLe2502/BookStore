package com.bookstore.admin.category;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

	@Query("SELECT c FROM Category c WHERE c.enabled = true ORDER BY c.name ASC")
	public List<Category> findAllEnabled();

	@Query("SELECT c FROM Category c WHERE c.enabled = true AND c.alias = ?1")
	public Category findByAliasEnabled(String alias);

	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL ORDER BY c.name ASC")
	public List<Category> findRootCategories();

	@Query("SELECT c FROM Category c WHERE c.name = ?1")
	public Category findByName(String name);

	@Query("SELECT c FROM Category c WHERE c.alias = ?1")
	public Category findByAlias(String alias);

	@Query("SELECT c FROM Category c WHERE c.parent.id = ?1 ORDER BY c.name ASC")
	public List<Category> findSubCategoriesById(Integer id);
}
