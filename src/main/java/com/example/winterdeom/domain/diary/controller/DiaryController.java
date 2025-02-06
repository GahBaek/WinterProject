package com.example.winterdeom.domain.diary.controller;

import com.example.winterdeom.domain.diary.dto.req.DiaryRequest;
import com.example.winterdeom.domain.diary.dto.res.DiaryResponse;
import com.example.winterdeom.domain.diary.service.DiaryService;
import com.example.winterdeom.domain.user.domain.User;
import com.example.winterdeom.global.auth.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/create")
    public ResponseEntity<DiaryResponse> createDiary(@AuthenticatedUser User user, @RequestBody DiaryRequest request) {
        DiaryResponse diaryResponse = diaryService.createDiary(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryResponse);
    }
}