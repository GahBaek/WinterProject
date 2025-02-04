package com.example.winterdeom.domain.common.exception;

import com.example.winterdeom.domain.common.error.ErrorCode;

public class DtoValidationException extends CustomException {
    public DtoValidationException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}