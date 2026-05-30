package com.example.PostgresqlDatabaseWithConfig.repositories;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.BookEntity;
import com.example.PostgresqlDatabaseWithConfig.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private final BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(final BookRepository underTest)
    {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled()
    {
        AuthorEntity authorEntity = TestDataUtil.createAuthor();
        BookEntity bookEntity = TestDataUtil.createBook(authorEntity);
        underTest.save(bookEntity);

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }
}
