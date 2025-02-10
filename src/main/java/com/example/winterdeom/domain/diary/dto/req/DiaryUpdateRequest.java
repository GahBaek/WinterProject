package com.example.winterdeom.domain.diary.dto.req;

import com.example.winterdeom.domain.diary.domain.Emotion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
@Schema(description = "일기 수정 요청 DTO")
public class DiaryUpdateRequest {
    @Schema(description = "감정", example = "SAD")
    private Emotion emotion;

    @Schema(description = "내용", example = "기분이 좋지 않다.")
    private String content;

    @Schema(description = "이미지 URL 목록", example = "['image3.jpg']")
    private String imageUrls;
}