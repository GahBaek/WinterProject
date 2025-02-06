package com.example.winterdeom.domain.diary.dto.res;

import com.example.winterdeom.domain.diary.domain.Emotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiarySummaryResponse {
    private Long diaryId;  // 다이어리 ID
    private Emotion emotion;  // 감정
    private String content;  // 내용
}
