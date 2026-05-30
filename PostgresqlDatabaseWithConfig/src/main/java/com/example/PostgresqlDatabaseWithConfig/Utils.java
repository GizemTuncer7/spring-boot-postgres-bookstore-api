package com.example.PostgresqlDatabaseWithConfig;

import com.example.PostgresqlDatabaseWithConfig.mappers.Mapper;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
    private Utils(){}

    public static <E, D> List<D> mapEntityListToDtoList(
            Iterable<E> entities,
            Mapper<E, D> mapper
    ) {
        List<D> dtos = new ArrayList<>();

        for (E entity : entities) {
            dtos.add(mapper.mapTo(entity));
        }

        return dtos;
    }
}
