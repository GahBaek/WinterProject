package com.example.winterdeom.domain.diary.dto.req;

import com.example.winterdeom.domain.diary.domain.Emotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DiaryUpdateRequest {
    private Emotion emotion; // 감정
    private String content; // 내용
    private String imageUrls; // 사진 리스트
}
