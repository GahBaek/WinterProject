package com.example.winterdeom.domain.diary.controller;

import com.example.winterdeom.domain.diary.domain.Emotion;
import com.example.winterdeom.domain.diary.dto.req.DiaryRequest;
import com.example.winterdeom.domain.diary.dto.req.DiaryUpdateRequest;
import com.example.winterdeom.domain.diary.dto.res.DiaryResponse;
import com.example.winterdeom.domain.diary.dto.res.DiaryPageResponse;
import com.example.winterdeom.domain.diary.service.DiaryService;
import com.example.winterdeom.domain.user.domain.User;
import com.example.winterdeom.global.auth.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diary")
@RequiredArgsConstructor
@Tag(name = "Diary API", description = "일기 관련 API")
public class DiaryController {
    private final DiaryService diaryService;

    @Operation(summary = "일기 생성", description = "새로운 일기를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "일기 생성 성공", content = @Content(schema = @Schema(implementation = DiaryResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<DiaryResponse> createDiary(@AuthenticatedUser User user, @RequestBody DiaryRequest request) {
        DiaryResponse diaryResponse = diaryService.createDiary(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryResponse);
    }

    @Operation(summary = "일기 수정", description = "특정 일기를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 수정 성공", content = @Content(schema = @Schema(implementation = DiaryResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "일기 찾을 수 없음", content = @Content)
    })
    @PutMapping("/update/{diaryId}")
    public ResponseEntity<DiaryResponse> updateDiary(@AuthenticatedUser User user,
                                                     @PathVariable Long diaryId,
                                                     @RequestBody DiaryUpdateRequest request) {
        DiaryResponse updatedDiary = diaryService.updateDiary(user, diaryId, request);
        return ResponseEntity.ok(updatedDiary);
    }

    @Operation(summary = "일기 삭제", description = "특정 일기를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "일기 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "일기 찾을 수 없음", content = @Content)
    })
    @DeleteMapping("/delete/{diaryId}")
    public ResponseEntity<Void> deleteDiary(@AuthenticatedUser User user,
                                            @PathVariable Long diaryId) {
        diaryService.deleteDiary(user, diaryId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "일기 상세 조회", description = "특정 일기의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 조회 성공", content = @Content(schema = @Schema(implementation = DiaryResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "일기 찾을 수 없음", content = @Content)
    })
    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryResponse> getDiary(@AuthenticatedUser User user, @PathVariable Long diaryId) {
        DiaryResponse diaryResponse = diaryService.getDiary(user, diaryId);
        return ResponseEntity.ok(diaryResponse);
    }

    @Operation(summary = "내 일기 목록 조회", description = "내가 작성한 일기를 페이지네이션하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 일기 목록 조회 성공", content = @Content(schema = @Schema(implementation = DiaryPageResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content)
    })
    @GetMapping("/my-diaries")
    public ResponseEntity<DiaryPageResponse> getMyDiaries(@AuthenticatedUser User user,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        DiaryPageResponse diaries = diaryService.getMyDiaries(user, page, size);
        return ResponseEntity.ok(diaries);
    }

    @Operation(summary = "감정별 일기 조회", description = "특정 감정을 가진 일기를 페이지네이션하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "감정별 일기 목록 조회 성공", content = @Content(schema = @Schema(implementation = DiaryPageResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content)
    })
    @GetMapping("/emotion")
    public ResponseEntity<DiaryPageResponse> getDiariesByEmotion(
            @AuthenticatedUser User user,
            @RequestParam(name = "emotion") Emotion emotion,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        DiaryPageResponse response = diaryService.getDiariesByEmotion(user, emotion, page, size);
        return ResponseEntity.ok(response);
    }
}