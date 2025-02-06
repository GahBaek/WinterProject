package com.example.winterdeom.domain.diary.domain.repository;

import com.example.winterdeom.domain.diary.domain.Diary;
import com.example.winterdeom.domain.diary.domain.Emotion;
import com.example.winterdeom.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DiaryRepository extends JpaRepository<Diary, UUID> {
    List<Diary> findByUser(User user);

    // 특정 감정을 가진 일기 목록 조회
    List<Diary> findByEmotion(Emotion emotion);

    // 특정 날짜 이후의 일기 목록 조회
    List<Diary> findByCreatedAtAfter(LocalDateTime date);
}
