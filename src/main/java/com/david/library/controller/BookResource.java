package com.david.library.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.david.library.entity.Book;
import com.david.library.exception.BookNotFoundException;
import com.david.library.repository.BookRepository;

@RestController
public class BookResource {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/books")
	public List<Book> retrieveAllStudents() {
		return bookRepository.findAll();
	}

	@GetMapping("/books/{id}")
	public Book retrieveBook(@PathVariable long id) {
		Optional<Book> student = bookRepository.findById(id);

		if (!student.isPresent())
			throw new BookNotFoundException("id-" + id);

		return student.get();
	}

	@DeleteMapping("/books/{id}")
	public void deleteStudent(@PathVariable long id) {
		bookRepository.deleteById(id);
	}

	@PostMapping("/books")
	public ResponseEntity<Object> createStudent(@RequestBody Book book) {
		Book savedStudent = bookRepository.save(book);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/books/{id}")
	public ResponseEntity<Object> updateBook(@RequestBody Book book, @PathVariable long id) {

		Optional<Book> bookOptional = bookRepository.findById(id);

		if (!bookOptional.isPresent())
			return ResponseEntity.notFound().build();

		book.setId(id);
		
		bookRepository.save(book);

		return ResponseEntity.noContent().build();
	}
}
