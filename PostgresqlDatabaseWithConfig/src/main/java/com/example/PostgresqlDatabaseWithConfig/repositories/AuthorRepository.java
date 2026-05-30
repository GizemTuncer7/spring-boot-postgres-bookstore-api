package com.example.PostgresqlDatabaseWithConfig.repositories;

import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    @Query("Select a From AuthorEntity a Where a.age > ?1")
    List<AuthorEntity> findAuthorsWithAgeGreaterThan(int i);

    List<AuthorEntity> findByAgeLessThan(int i);
}
