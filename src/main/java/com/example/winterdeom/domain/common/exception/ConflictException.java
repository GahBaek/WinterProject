package com.example.winterdeom.domain.common.exception;


import com.example.winterdeom.domain.common.error.ErrorCode;

public class ConflictException extends CustomException {

    public ConflictException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}