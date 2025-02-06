package com.example.winterdeom.domain.diary.domain;

import com.example.winterdeom.domain.common.BaseEntity;
import com.example.winterdeom.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Statistics extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 감정 통계를 가진 사용자

    @Column(nullable = false)
    private LocalDate month;  // 월별 통계

    @OneToMany(mappedBy = "statistics", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmotionStatistics> emotionCounts = new ArrayList<>();
}
