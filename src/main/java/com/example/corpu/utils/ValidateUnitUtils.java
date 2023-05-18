package com.example.corpu.utils;

import com.example.corpu.constants.ErrorConstant;
import com.example.corpu.unit.UnitDTO;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

public class ValidateUnitUtils {
    public static ValidateResultDTO validatesInput(UnitDTO unitDTO) {
        boolean status = true;
        String errorCode = null;
        String errorLabel = null;
        if (Objects.isNull(unitDTO.getName()) || unitDTO.getName().isEmpty()) {
            errorCode = ErrorConstant.BLANK_FIELD;
            errorLabel = String.format(ErrorConstant.BLANK_FIELD_LABEL, "name");
        }

        if (errorCode != null) {
            status = false;
        }
        return ValidateResultDTO.builder()
                .status(status)
                .errorCode(errorCode)
                .errorLabel(errorLabel)
                .build();

    }
}
