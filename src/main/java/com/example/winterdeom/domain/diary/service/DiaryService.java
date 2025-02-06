package com.example.winterdeom.domain.diary.service;

import com.example.winterdeom.domain.common.error.ErrorCode;
import com.example.winterdeom.domain.common.exception.ForbiddenException;
import com.example.winterdeom.domain.common.exception.NotFoundException;
import com.example.winterdeom.domain.common.exception.UnauthorizedException;
import com.example.winterdeom.domain.diary.domain.Diary;
import com.example.winterdeom.domain.diary.domain.repository.DiaryRepository;
import com.example.winterdeom.domain.diary.dto.req.DiaryRequest;
import com.example.winterdeom.domain.diary.dto.req.DiaryUpdateRequest;
import com.example.winterdeom.domain.diary.dto.res.DiaryResponse;
import com.example.winterdeom.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;


    //일기 작성
    public DiaryResponse createDiary(User user, DiaryRequest request) {
        if (user == null) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_USER);
        }

        Diary diary = Diary.builder()
                .user(user)
                .emotion(request.getEmotion())
                .content(request.getContent())
                .imageUrls(request.getImageUrls())
                .build();

        diaryRepository.save(diary);
        return convertToDiaryResponse(diary);
    }
    //일기 수정
    @Transactional
    public DiaryResponse updateDiary(User user, Long diaryId, DiaryUpdateRequest request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));

        if (!diary.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_USER);
        }

        diary.update(request.getEmotion(), request.getContent(), request.getImageUrls());

        return convertToDiaryResponse(diary);
    }



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