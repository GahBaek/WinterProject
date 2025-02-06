package com.example.winterdeom.domain.diary.dto.res;

import com.example.winterdeom.domain.diary.domain.Emotion;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponse {
    private Long diaryId;
    private String email; // 작성자 이메일
    private Emotion emotion; //감정이모티콘
    private String content; //내용
    private String imageUrls; //사진
    private LocalDateTime createdTime; //생성시간
}
