package com.example.PostgresqlDatabaseWithConfig.domain.dto;

import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private String isbn;

    private String title;

    @JsonIgnoreProperties("books")
    private AuthorEntity authorEntity;
}
