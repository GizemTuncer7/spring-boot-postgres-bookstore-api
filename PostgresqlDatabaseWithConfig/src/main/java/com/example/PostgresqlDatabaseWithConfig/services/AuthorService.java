package com.example.PostgresqlDatabaseWithConfig.services;

import com.example.PostgresqlDatabaseWithConfig.domain.dto.AuthorDto;
import com.example.PostgresqlDatabaseWithConfig.domain.dto.DeleteResultDto;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService extends Service {
    AuthorEntity createAuthor(AuthorEntity author);

    Iterable<AuthorEntity> getAllAuthors();

    AuthorEntity getAuthorById(Long id);

    AuthorEntity updateAuthorAgeById(Integer age, Long id);

    AuthorEntity updateAuthorNameById(String name, Long id);

    AuthorEntity updateAuthorById(AuthorDto author, Long id);

    DeleteResultDto deleteAuthorWithId(Long id);

    List<BookEntity> getAuthorBooksById(Long id);
}
