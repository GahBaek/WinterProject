package com.example.winterdeom.domain.diary.dto.req;

import com.example.winterdeom.domain.diary.domain.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일기 생성 요청 DTO")
public class DiaryRequest {
    @Schema(description = "감정", example = "HAPPY")
    private Emotion emotion;

    @Schema(description = "내용", example = "오늘 하루는 즐거웠다.")
    private String content;

    @Schema(description = "이미지 URL 목록", example = "['image1.jpg', 'image2.jpg']")
    private String imageUrls;
}