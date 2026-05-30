package com.example.PostgresqlDatabaseWithConfig.services.impl;

import com.example.PostgresqlDatabaseWithConfig.domain.dto.AuthorDto;
import com.example.PostgresqlDatabaseWithConfig.domain.dto.DeleteResultDto;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.BookEntity;
import com.example.PostgresqlDatabaseWithConfig.repositories.AuthorRepository;
import com.example.PostgresqlDatabaseWithConfig.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository)
    {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public Iterable<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public AuthorEntity getAuthorById(Long id) {
        Optional<AuthorEntity> authorEntity =  authorRepository.findById(id);
        return authorEntity.orElse(null);
    }

    @Override
    public AuthorEntity updateAuthorAgeById(Integer age, Long id) {
        AuthorEntity authorEntity = getAuthorById(id);
        authorEntity.setAge(age);
        return authorRepository.save(authorEntity);
    }

    @Override
    public AuthorEntity updateAuthorNameById(String name, Long id) {
        AuthorEntity authorEntity = getAuthorById(id);
        authorEntity.setName(name);
        return authorRepository.save(authorEntity);
    }

    @Override
    public AuthorEntity updateAuthorById(AuthorDto author, Long id) {
        AuthorEntity authorEntity = getAuthorById(id);
        authorEntity.setName(author.getName());
        authorEntity.setAge(author.getAge());
        return authorRepository.save(authorEntity);
    }

    @Override
    public DeleteResultDto deleteAuthorWithId(Long id) {
        AuthorEntity authorEntity = getAuthorById(id);
        List<BookEntity> bookEntitiesOfAuthorEntity = authorEntity.getBooks();
        if(!bookEntitiesOfAuthorEntity.isEmpty())
        {
            return new DeleteResultDto(false);
        }
        else
        {
            authorRepository.deleteById(id);
            return new DeleteResultDto(getAuthorById(id) == null);
        }
    }

    @Override
    public List<BookEntity> getAuthorBooksById(Long id) {
        AuthorEntity authorEntity = getAuthorById(id);
        return authorEntity.getBooks();
    }
}
