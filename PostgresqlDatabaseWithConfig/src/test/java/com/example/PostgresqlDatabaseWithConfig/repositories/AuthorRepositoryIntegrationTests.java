package com.example.PostgresqlDatabaseWithConfig.repositories;

import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import com.example.PostgresqlDatabaseWithConfig.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class AuthorRepositoryIntegrationTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(final AuthorRepository underTest)
    {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled()
    {
        AuthorEntity authorEntity = TestDataUtil.createAuthor();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatMultipleAuthorCanBeCreatedAndRecalled()
    {
        List<String> names = new ArrayList<>(List.of("AuthorA","AuthorB"));
        List<Integer> ages = new ArrayList<>(List.of(25, 30));
        List<AuthorEntity> authorEntities = TestDataUtil.createAuthors(names, ages);

        for (AuthorEntity authorEntity : authorEntities) {
            underTest.save(authorEntity);
        }

        List<AuthorEntity> results = (List<AuthorEntity>) underTest.findAll();

        assertThat(results).isEqualTo(authorEntities);
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    public void testThatAuthorCanBeUpdated()
    {
        AuthorEntity authorEntity = TestDataUtil.createAuthor();
        underTest.save(authorEntity);

        authorEntity.setName("Dündaar");

        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatAuthorCanBeDeleted()
    {
        AuthorEntity authorEntity = TestDataUtil.createAuthor();
        underTest.save(authorEntity);

        Long currentAuthorId = authorEntity.getId();
        underTest.deleteById(currentAuthorId);

        Optional<AuthorEntity> result = underTest.findById(currentAuthorId);
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatAuthorWithAgeGreaterThan()
    {
        List<String> names = new ArrayList<>(List.of("Gizem", "Dündar", "Şans"));
        List<Integer> ages = new ArrayList<>(List.of(25, 16, 3));

        List<AuthorEntity> authorEntities = TestDataUtil.createAuthors(names, ages);
        for(AuthorEntity authorEntity : authorEntities)
        {
            underTest.save(authorEntity);
        }
        List<AuthorEntity> customResult = underTest.findByAgeLessThan(10);
        List<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(10);

        assertThat(result.size()).isEqualTo(2);
        assertThat(customResult.size()).isEqualTo(1);
    }
}
