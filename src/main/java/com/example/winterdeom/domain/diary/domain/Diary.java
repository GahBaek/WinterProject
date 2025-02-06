package com.example.winterdeom.domain.diary.domain;

import com.example.winterdeom.domain.common.BaseEntity;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;  // 감정

    @Column(columnDefinition = "TEXT")
    private String content;  // 일기 내용

    @ElementCollection
    private List<String> imageUrls;  // 이미지 리스트

}
