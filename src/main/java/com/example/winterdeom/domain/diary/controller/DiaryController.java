package com.example.winterdeom.domain.diary.controller;

import com.example.winterdeom.domain.diary.dto.req.DiaryRequest;
import com.example.winterdeom.domain.diary.dto.req.DiaryUpdateRequest;
import com.example.winterdeom.domain.diary.dto.res.DiaryResponse;
import com.example.winterdeom.domain.diary.dto.res.DiaryPageResponse;
import com.example.winterdeom.domain.diary.service.DiaryService;
import com.example.winterdeom.domain.user.domain.User;
import com.example.winterdeom.global.auth.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update/{diaryId}")
    public ResponseEntity<DiaryResponse> updateDiary(@AuthenticatedUser User user,
                                                     @PathVariable Long diaryId,
                                                     @RequestBody DiaryUpdateRequest request) {
        DiaryResponse updatedDiary = diaryService.updateDiary(user, diaryId, request);
        return ResponseEntity.ok(updatedDiary);
    }
    @DeleteMapping("/delete/{diaryId}")
    public ResponseEntity<Void> deleteDiary(@AuthenticatedUser User user,
                                            @PathVariable Long diaryId) {
        diaryService.deleteDiary(user, diaryId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryResponse> getDiary(@AuthenticatedUser User user, @PathVariable Long diaryId) {
        DiaryResponse diaryResponse = diaryService.getDiary(user, diaryId);
        return ResponseEntity.ok(diaryResponse);
    }
    @GetMapping("/my-diaries")
    public ResponseEntity<DiaryPageResponse> getMyDiaries(@AuthenticatedUser User user,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        DiaryPageResponse diaries = diaryService.getMyDiaries(user, page, size);
        return ResponseEntity.ok(diaries);
    }

}