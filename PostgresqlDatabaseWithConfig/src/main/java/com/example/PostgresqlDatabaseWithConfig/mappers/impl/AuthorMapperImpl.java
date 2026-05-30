package com.example.PostgresqlDatabaseWithConfig.mappers.impl;

import com.example.PostgresqlDatabaseWithConfig.domain.dto.AuthorDto;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.AuthorEntity;
import com.example.PostgresqlDatabaseWithConfig.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {
    private final ModelMapper modelMapper;

    public AuthorMapperImpl(final ModelMapper modelMapper)
    {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        try
        {
            return modelMapper.map(authorEntity, AuthorDto.class);
        }
        catch (IllegalArgumentException error)
        {
            System.out.println(error.getMessage());
            return null;
        }
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        try
        {
            return modelMapper.map(authorDto,  AuthorEntity.class);
        }
        catch (IllegalArgumentException error)
        {
            System.out.println(error.getMessage());
            return null;
        }
    }
}
