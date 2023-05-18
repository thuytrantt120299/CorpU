package com.example.corpu.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateResultDTO {
    private Boolean status;
    private String errorCode;
    private String errorLabel;
}
