package com.example.PostgresqlDatabaseWithConfig.services;

import com.example.PostgresqlDatabaseWithConfig.domain.dto.DeleteResultDto;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.BookEntity;

import java.util.List;

public interface BookService extends Service{
    BookEntity createBook(String isbn, BookEntity bookEntity);

    BookEntity createBookWithAuthor(String isbn, BookEntity bookEntity, Long authorId);

    Iterable<BookEntity> getBooks();

    Iterable<BookEntity> getBooksWithAuthorId(Long authorId);

    BookEntity getBookWithIsbn(String isbn);

    BookEntity updateBookTitleByIsbn(String isbn, String title);

    DeleteResultDto deleteBookByIsbn(String isbn);
}
