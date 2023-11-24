package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResponse {
    private Boolean success;
    private String message;
    private Object data;

    public CommonResponse(Object data) {
        this.success = true;
        this.message = "Success";
        this.data = data;
    }
}
