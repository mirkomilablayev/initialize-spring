package com.example.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;



@NoArgsConstructor
@Getter
@Setter
public class GenericException extends RuntimeException {
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private String message = "Nomalum xatolik!";

    public GenericException(String message) {
        this.message = message;
    }

    public GenericException(HttpStatus httpStatus,String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
