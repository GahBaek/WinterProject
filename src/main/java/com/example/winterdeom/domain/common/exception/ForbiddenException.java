package com.example.winterdeom.domain.common.exception;

import com.example.winterdeom.domain.common.error.ErrorCode;

public class ForbiddenException extends CustomException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}