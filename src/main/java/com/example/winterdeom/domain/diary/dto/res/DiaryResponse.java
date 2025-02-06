package com.example.winterdeom.domain.diary.dto.res;

import com.example.winterdeom.domain.diary.domain.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일기 상세 응답 DTO")
public class DiaryResponse {
    @Schema(description = "일기 ID", example = "1")
    private Long diaryId;

    @Schema(description = "작성자 이메일", example = "user@example.com")
    private String email;

    @Schema(description = "감정", example = "EXCITED")
    private Emotion emotion;

    @Schema(description = "내용", example = "새로운 프로젝트를 시작했다!")
    private String content;

    @Schema(description = "이미지 URL 목록", example = "url")
    private String imageUrls;

    @Schema(description = "생성 시간", example = "2025-02-07T12:30:00")
    private LocalDateTime createdTime;
}