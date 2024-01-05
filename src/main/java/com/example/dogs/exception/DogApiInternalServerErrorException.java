package com.example.dogs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class DogApiInternalServerErrorException extends RuntimeException {
        private final HttpStatus status;
        private final String message;

        public DogApiInternalServerErrorException(HttpStatus status, String message) {
            super(message);
            this.status = status;
            this.message = message;
        }
        @Override
        public synchronized Throwable fillInStackTrace(){
            return null;
        }
}


