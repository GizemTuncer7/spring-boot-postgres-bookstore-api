package com.example.PostgresqlDatabaseWithConfig.domain.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DeleteResultDto {
    private boolean success;
    private HttpStatus message;

    public DeleteResultDto(boolean success)
    {
        if(success) { this.message = HttpStatus.OK;}
        else { message = HttpStatus.BAD_REQUEST; }
        this.success = success;
    }
}