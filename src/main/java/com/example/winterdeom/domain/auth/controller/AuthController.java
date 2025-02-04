package com.example.winterdeom.domain.auth.controller;

import com.example.winterdeom.domain.auth.dto.request.LoginDto;
import com.example.winterdeom.domain.auth.service.AuthService;
import com.example.winterdeom.domain.common.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    //회원가입
    @PostMapping("auth/join")
    public ResponseEntity<ResponseDto<Void>> join(@Valid @RequestBody LoginDto joinDto, HttpServletResponse response) {
        this.authService.join(joinDto, response);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "login successfully"), HttpStatus.OK);
    }
    //로그인
    @PostMapping("/auth/login")
    public ResponseEntity<ResponseDto<Void>> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        this.authService.login(loginDto, response);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "login successfully"), HttpStatus.OK);
    }
}
