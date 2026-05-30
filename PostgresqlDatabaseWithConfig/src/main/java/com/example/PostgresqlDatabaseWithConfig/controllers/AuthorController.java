package com.example.PostgresqlDatabaseWithConfig.controllers;

import com.example.PostgresqlDatabaseWithConfig.Utils;
import com.example.PostgresqlDatabaseWithConfig.domain.dto.AuthorDto;
import com.example.PostgresqlDatabaseWithConfig.domain.dto.BookDto;
import com.example.PostgresqlDatabaseWithConfig.domain.dto.DeleteResultDto;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.BookEntity;
import com.example.PostgresqlDatabaseWithConfig.mappers.Mapper;
import com.example.PostgresqlDatabaseWithConfig.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;
    private final Mapper<BookEntity, BookDto> bookMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper, Mapper<BookEntity, BookDto> bookMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAuthors() {
        Iterable<AuthorEntity> authorEntities = authorService.getAllAuthors();
        return new ResponseEntity<>(Utils.mapEntityListToDtoList(authorEntities, authorMapper), HttpStatus.OK);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        AuthorEntity authorEntity = authorService.getAuthorById(id);
        return new ResponseEntity<>(authorMapper.mapTo(authorEntity), HttpStatus.OK);
    }

    @GetMapping("authors/book/{id}")
    public ResponseEntity<List<BookDto>> getAuthorBooksById(@PathVariable("id") Long id)
    {
        List<BookEntity> bookEntities = authorService.getAuthorBooksById(id);
        return new ResponseEntity<>(Utils.mapEntityListToDtoList(bookEntities, bookMapper), HttpStatus.OK);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthorById(@RequestBody AuthorDto author, @PathVariable("id") Long id)
    {
        AuthorEntity updatedAuthorEntity = authorService.updateAuthorById(author, id);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);
    }


    @PatchMapping(value = "/authors/{id}", params = "name")
    public ResponseEntity<AuthorDto> updateAuthorNameById(@RequestParam String name, @PathVariable("id") Long id)
    {
        AuthorEntity updatedAuthorEntity = authorService.updateAuthorNameById(name, id);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);
    }

    @PatchMapping(value = "/authors/{id}", params = "age")
    public ResponseEntity<AuthorDto> updateAuthorAgeById(@RequestParam Integer age, @PathVariable("id") Long id)
    {
        AuthorEntity updatedAuthorEntity = authorService.updateAuthorAgeById(age, id);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<HttpStatus> deleteAuthorById(@PathVariable("id") Long id)
    {
        DeleteResultDto result = authorService.deleteAuthorWithId(id);
        return new ResponseEntity<>(result.getMessage());
    }
}
