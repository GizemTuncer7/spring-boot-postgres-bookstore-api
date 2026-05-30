package com.example.PostgresqlDatabaseWithConfig.mappers.impl;

import com.example.PostgresqlDatabaseWithConfig.domain.dto.BookDto;
import com.example.PostgresqlDatabaseWithConfig.domain.entities.BookEntity;
import com.example.PostgresqlDatabaseWithConfig.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        try{ return modelMapper.map(bookEntity, BookDto.class);}
        catch(IllegalArgumentException error) {System.out.println(error.getMessage()); return null;}
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        try{return modelMapper.map(bookDto, BookEntity.class);}
        catch(IllegalArgumentException error) {System.out.println(error.getMessage()); return null;}
    }
}
