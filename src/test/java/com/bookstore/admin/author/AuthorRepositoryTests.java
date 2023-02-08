package com.bookstore.admin.author;



import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bookstore.admin.entity.Author;




@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AuthorRepositoryTests {
	@Autowired private AuthorRepository repo;
	
	@Test
	public void testFindAllAuthors() {
		List<Author> authors = repo.findAll();
		authors.forEach(author -> {
			System.out.println(author.getName());
		});
	}
}
