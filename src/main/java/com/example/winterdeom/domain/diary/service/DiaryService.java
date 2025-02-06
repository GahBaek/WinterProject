package com.example.winterdeom.domain.diary.service;

import com.example.winterdeom.domain.common.error.ErrorCode;
import com.example.winterdeom.domain.common.exception.ForbiddenException;
import com.example.winterdeom.domain.common.exception.NotFoundException;
import com.example.winterdeom.domain.common.exception.UnauthorizedException;
import com.example.winterdeom.domain.diary.domain.Diary;
import com.example.winterdeom.domain.diary.domain.Emotion;
import com.example.winterdeom.domain.diary.domain.repository.DiaryRepository;
import com.example.winterdeom.domain.diary.dto.req.DiaryRequest;
import com.example.winterdeom.domain.diary.dto.req.DiaryUpdateRequest;
import com.example.winterdeom.domain.diary.dto.res.DiaryResponse;
import com.example.winterdeom.domain.diary.dto.res.DiaryPageResponse;
import com.example.winterdeom.domain.diary.dto.res.DiarySummaryResponse;
import com.example.winterdeom.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public void deleteDiary(User user, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));

        if (!diary.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_USER);
        }

        diaryRepository.delete(diary);
    }
    @Transactional(readOnly = true)
    public DiaryResponse getDiary(User user, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));

        if (!diary.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_USER);
        }

        return convertToDiaryResponse(diary);
    }



    @Transactional(readOnly = true)
    public DiaryPageResponse getMyDiaries(User user, int page, int size) {
        if (user == null) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_USER);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Diary> diaryPage = diaryRepository.findByUser(user, pageable);
        return convertToDiaryPageResponse(diaryPage);
    }

    @Transactional(readOnly = true)
    public DiaryPageResponse getDiariesByEmotion(User user, Emotion emotion, int page, int size) {
        if (user == null) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_USER);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Diary> diaryPage = diaryRepository.findByEmotion(emotion, pageable);

        List<Diary> userDiaries = diaryPage.getContent().stream()
                .filter(diary -> diary.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        Page<Diary> filteredPage = new PageImpl<>(userDiaries, pageable, userDiaries.size());
        return convertToDiaryPageResponse(filteredPage);
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

    private DiaryPageResponse convertToDiaryPageResponse(Page<Diary> diaryPage) {
        return DiaryPageResponse.builder()
                .content(diaryPage.getContent().stream()
                        .map(this::convertToDiarySummaryResponse)
                        .collect(Collectors.toList())) // 개별 요약 리스트 변환
                .currentPage(diaryPage.getNumber()) // 현재 페이지
                .totalPages(diaryPage.getTotalPages()) // 전체 페이지 수
                .totalElements(diaryPage.getTotalElements()) // 전체 개수
                .last(diaryPage.isLast()) // 마지막 페이지 여부
                .build();
    }
    private DiarySummaryResponse convertToDiarySummaryResponse(Diary diary) {
        return DiarySummaryResponse.builder()
                .diaryId(diary.getId())
                .emotion(diary.getEmotion())
                .content(diary.getContent())
                .build();
    }


}