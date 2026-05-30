package com.example.PostgresqlDatabaseWithConfig;

import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.BookEntity;

import java.util.ArrayList;
import java.util.List;

public final class TestDataUtil {

    private TestDataUtil(){}


    public static AuthorEntity createAuthor() {
        return AuthorEntity.builder()
                .name("Gizem")
                .age(25)
                .build();
    }

    public static List<AuthorEntity> createAuthors(List<String> names, List<Integer> ages)
    {
        List<AuthorEntity> createdAuthorsList = new ArrayList<AuthorEntity>();
        for(int i = 0; i < names.size(); i++)
        {
            AuthorEntity authorEntity = AuthorEntity.builder()
                    .name(names.get(i))
                    .age(ages.get(i))
                    .build();
            createdAuthorsList.add(authorEntity);
        }
        return createdAuthorsList;
    }

    public static BookEntity createBook(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("123456789")
                .title("First Book")
                .authorEntity(authorEntity)
                .build();
    }
}
