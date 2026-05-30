package com.example.PostgresqlDatabaseWithConfig.services.impl;

import com.example.PostgresqlDatabaseWithConfig.domain.dto.DeleteResultDto;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.BookEntity;
import com.example.PostgresqlDatabaseWithConfig.repositories.AuthorRepository;
import com.example.PostgresqlDatabaseWithConfig.repositories.BookRepository;
import com.example.PostgresqlDatabaseWithConfig.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public BookEntity createBookWithAuthor(String isbn, BookEntity bookEntity, Long authorId) {
        Optional<AuthorEntity> authorEntity = authorRepository.findById(authorId);
        bookEntity.setIsbn(isbn);
        bookEntity.setAuthorEntity(authorEntity.orElse(null));
        return bookRepository.save(bookEntity);
    }

    @Override
    public Iterable<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Iterable<BookEntity> getBooksWithAuthorId(Long authorId) {
        Optional<AuthorEntity> authorEntity = authorRepository.findById(authorId);
        return bookRepository.findByAuthorEntity(authorEntity);
    }

    @Override
    public BookEntity getBookWithIsbn(String isbn) {
        return bookRepository.findById(isbn).orElse(null);
    }

    @Override
    public BookEntity updateBookTitleByIsbn(String isbn, String title) {
        BookEntity bookEntity = getBookWithIsbn(isbn);
        bookEntity.setTitle(title);
        return bookRepository.save(bookEntity);
    }

    @Override
    public DeleteResultDto deleteBookByIsbn(String isbn) {
        BookEntity bookEntity = getBookWithIsbn(isbn);
        bookRepository.delete(bookEntity);
        return new DeleteResultDto(getBookWithIsbn(isbn) == null);
    }
}
