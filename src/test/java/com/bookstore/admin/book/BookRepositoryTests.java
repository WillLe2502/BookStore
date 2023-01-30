package com.bookstore.admin.book;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bookstore.admin.entity.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookRepositoryTests {
	@Autowired BookRepository repo;

	@Test
	public void testFindByName() {
		String name = "Alone With You In The Ether";
		Book book = repo.findByName(name);

		assertThat(book).isNotNull();
	}
}
