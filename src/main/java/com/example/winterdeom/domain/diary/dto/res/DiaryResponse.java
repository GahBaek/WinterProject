package com.example.winterdeom.domain.diary.dto.res;

import com.example.winterdeom.domain.diary.domain.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
}