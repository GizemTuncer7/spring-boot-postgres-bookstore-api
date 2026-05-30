package com.example.PostgresqlDatabaseWithConfig.repositories;

import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {
    Iterable<BookEntity> findByAuthorEntity(Optional<AuthorEntity> authorEntity);
}
