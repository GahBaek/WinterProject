package com.example.winterdeom.domain.diary.domain.repository;

import com.example.winterdeom.domain.diary.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    // 특정 월의 감정 통계 조회
    Optional<Statistics> findByMonth(LocalDate month);
}
