package com.example.winterdeom.domain.diary.domain.repository;

import com.example.winterdeom.domain.diary.domain.Emotion;
import com.example.winterdeom.domain.diary.domain.EmotionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface EmotionStatisticsRepository extends JpaRepository<EmotionStatistics, UUID> {

    // 특정 감정에 대한 총 발생 횟수 조회
    @Query("SELECT SUM(e.count) FROM EmotionStatistics e WHERE e.emotion = :emotion")
    Integer getTotalEmotionCount(@Param("emotion") Emotion emotion);

    // 특정 월과 감정에 대한 통계 조회
    @Query("SELECT e FROM EmotionStatistics e JOIN e.statistics s WHERE s.month = :month AND e.emotion = :emotion")
    Optional<EmotionStatistics> findByMonthAndEmotion(@Param("month") LocalDate month, @Param("emotion") Emotion emotion);
}
