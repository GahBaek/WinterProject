package com.example.winterdeom.domain.diary.domain;

import com.example.winterdeom.domain.common.BaseEntity;
import com.example.winterdeom.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diary extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 작성한 사용자

    @Enumerated(EnumType.STRING)
    private Emotion emotion;  // 감정

    @Column(columnDefinition = "TEXT")
    private String content;  // 일기 내용

    private String imageUrls; //이미지 파일

}
