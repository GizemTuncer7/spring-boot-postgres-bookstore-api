package com.example.PostgresqlDatabaseWithConfig.controllers;

import com.example.PostgresqlDatabaseWithConfig.Utils;
import com.example.PostgresqlDatabaseWithConfig.domain.dto.BookDto;
import com.example.PostgresqlDatabaseWithConfig.domain.dto.DeleteResultDto;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.BookEntity;
import com.example.PostgresqlDatabaseWithConfig.mappers.Mapper;
import com.example.PostgresqlDatabaseWithConfig.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;
    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto)
    {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/books/{isbn}", params = "authorId")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto, @RequestParam("authorId") Long authorId)
    {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createBookWithAuthor(isbn, bookEntity, authorId);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getBooks()
    {
        Iterable<BookEntity> bookEntities = bookService.getBooks();
        return new ResponseEntity<>(Utils.mapEntityListToDtoList(bookEntities, bookMapper), HttpStatus.OK);
    }

    @GetMapping(value = "/books", params = "authorId")
    public ResponseEntity<List<BookDto>> getBooksByAuthorId(@RequestParam("authorId") Long authorId)
    {
        Iterable<BookEntity> bookEntities = bookService.getBooksWithAuthorId(authorId);
        return new ResponseEntity<>(Utils.mapEntityListToDtoList(bookEntities, bookMapper), HttpStatus.OK);
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable("isbn") String isbn)
    {
        BookEntity bookEntity = bookService.getBookWithIsbn(isbn);
        return new ResponseEntity<>(bookMapper.mapTo(bookEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/books/{isbn}", params = "title")
    public ResponseEntity<BookDto> updateBookTitleByIsbn(@PathVariable("isbn") String isbn, @RequestParam("title") String title)
    {
        BookEntity savedBookEntity = bookService.updateBookTitleByIsbn(isbn, title);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.OK);
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<HttpStatus> deleteBookByIsbn(@PathVariable("isbn") String isbn)
    {
        DeleteResultDto result = bookService.deleteBookByIsbn(isbn);
        return new ResponseEntity<>(result.getMessage());
    }
}
