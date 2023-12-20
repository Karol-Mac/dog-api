package com.example.dogs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DogApiNotFoundException extends RuntimeException{
    private final HttpStatus status;
    private final String resource;
    private final String fieldName;

    public DogApiNotFoundException(HttpStatus status , String resource, String fieldName) {
        super(resource+" is not found with "+fieldName);
        this.resource = resource;
        this.fieldName = fieldName;
        this.status = status;
    }
}
