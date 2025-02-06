package com.example.winterdeom.domain.diary.domain;

import com.example.winterdeom.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmotionStatistics extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "statistics_id", nullable = false)
    private Statistics statistics;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Emotion emotion;

    @Column(nullable = false)
    private Integer count;  // 감정별 횟수 저장
}
