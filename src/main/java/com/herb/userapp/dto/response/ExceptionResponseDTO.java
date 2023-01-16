package com.herb.userapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseDTO {
    private HttpStatus status;
    private String error;
    private String message;
}
