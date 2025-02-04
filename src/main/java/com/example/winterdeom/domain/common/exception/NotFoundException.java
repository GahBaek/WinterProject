package com.example.winterdeom.domain.common.exception;

import com.example.winterdeom.domain.common.error.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

