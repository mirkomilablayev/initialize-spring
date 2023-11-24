package com.example.exceptions;

import com.example.dto.CommonResponse;
import com.example.util.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonResponse> handleException(Exception ex) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setSuccess(false);
        commonResponse.setData(null);

        if (ex instanceof GenericException) {
            GenericException genericException = (GenericException) ex;
            commonResponse.setMessage(genericException.getMessage());
            log.error("ERROR_TIME: " + ConvertUtils.getCurrentTashkentTime() + " // ERROR: " + commonResponse.getMessage());
            return ResponseEntity.status(genericException.getHttpStatus()).body(commonResponse);
        } else {
            int status = 500;
            if (ex instanceof HttpClientErrorException) {
                status = ((HttpClientErrorException) ex).getStatusCode().value();
            } else if (ex instanceof HttpServerErrorException) {
                status = ((HttpServerErrorException) ex).getStatusCode().value();
            } else if (ex instanceof HttpStatusCodeException) {
                status = ((HttpStatusCodeException) ex).getStatusCode().value();
            }else if (ex instanceof InternalAuthenticationServiceException){
                status = 404;
            }

            commonResponse.setMessage(ex.getMessage());
            log.error("ERROR_TIME: " + ConvertUtils.getCurrentTashkentTime() + " // ERROR: " + commonResponse.getMessage());
            return ResponseEntity.status(status).body(commonResponse);
        }
    }

}
