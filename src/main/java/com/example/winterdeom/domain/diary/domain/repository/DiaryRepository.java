package com.example.winterdeom.domain.diary.domain.repository;

import com.example.winterdeom.domain.diary.domain.Diary;
import com.example.winterdeom.domain.diary.domain.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    // 특정 감정을 가진 일기 목록 조회
    List<Diary> findByEmotion(Emotion emotion);

    // 특정 날짜 이후의 일기 목록 조회
    List<Diary> findByCreatedAtAfter(LocalDateTime date);
}
