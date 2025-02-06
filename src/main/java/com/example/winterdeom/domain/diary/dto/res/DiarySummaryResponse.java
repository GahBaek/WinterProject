package com.example.winterdeom.domain.diary.dto.res;

import com.example.winterdeom.domain.diary.domain.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "일기 간단 응답 DTO")
public class DiarySummaryResponse {
    @Schema(description = "일기 ID", example = "5")
    private Long diaryId;

    @Schema(description = "감정", example = "HAPPY")
    private Emotion emotion;

    @Schema(description = "내용", example = "오늘 하루가 좋았다!")
    private String content;
}
