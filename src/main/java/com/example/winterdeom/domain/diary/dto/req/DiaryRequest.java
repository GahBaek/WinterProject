package com.example.winterdeom.domain.diary.dto.req;

import com.example.winterdeom.domain.diary.domain.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRequest {
    private Emotion emotion; //감정
    private String content; //내용
    private String imageUrls; //사진
}
