package com.example.winterdeom.domain.diary.service;

import com.example.winterdeom.domain.diary.domain.Diary;
import com.example.winterdeom.domain.diary.domain.repository.DiaryRepository;
import com.example.winterdeom.domain.diary.dto.req.DiaryRequest;
import com.example.winterdeom.domain.diary.dto.res.DiaryResponse;
import com.example.winterdeom.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;


    //일기 작성
    public DiaryResponse createDiary(User user, DiaryRequest request) {
        Diary diary = Diary.builder()
                .user(user)
                .emotion(request.getEmotion())
                .content(request.getContent())
                .imageUrls(request.getImageUrls())
                .build();
        diaryRepository.save(diary);
        return convertToDiaryResponse(diary);
    }

    /**
     * ✅ Diary → DiaryResponse 변환
     */
    private DiaryResponse convertToDiaryResponse(Diary diary) {
        return DiaryResponse.builder()
                .diaryId(diary.getId())
                .email(diary.getUser().getEmail())
                .emotion(diary.getEmotion())
                .content(diary.getContent())
                .imageUrls(diary.getImageUrls())
                .createdTime(diary.getCreatedAt())
                .build();
    }
}