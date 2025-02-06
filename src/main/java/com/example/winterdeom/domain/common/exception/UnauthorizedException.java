package com.example.winterdeom.domain.common.exception;

import com.example.winterdeom.domain.common.error.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

}

