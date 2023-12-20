package com.example.dogs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DogApiNotFoundException extends RuntimeException{
    private HttpStatus status;
    private String resource;
    private String fieldName;

    public DogApiNotFoundException(HttpStatus status , String resource, String fieldName) {
        super(resource+" is not found with "+fieldName);
        this.resource = resource;
        this.fieldName = fieldName;
        this.status = status;
    }

    public String getResource() {
        return resource;
    }

    public String getFieldName() {
        return fieldName;
    }
    public HttpStatus getStatus() {
        return status;
    }


}
