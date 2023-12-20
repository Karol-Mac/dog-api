package com.example.dogs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DogApiBadRequestException extends RuntimeException {
    //jak jest finale to nie mozna zmienic jej wartosci
    private final HttpStatus status;
    private final String message;

    public DogApiBadRequestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
