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

    // 일기 작성
    @Transactional
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

    // 일기 수정
    @Transactional
    public DiaryResponse updateDiary(User user, Long diaryId, DiaryUpdateRequest request) {
        Diary diary = findDiaryOrThrow(diaryId);
        validateDiaryOwner(user, diary);

        diary.update(request.getEmotion(), request.getContent(), request.getImageUrls());

        return convertToDiaryResponse(diary);
    }

    // 일기 삭제
    @Transactional
    public void deleteDiary(User user, Long diaryId) {
        Diary diary = findDiaryOrThrow(diaryId);
        validateDiaryOwner(user, diary);

        diaryRepository.delete(diary);
    }

    // 일기 조회
    @Transactional(readOnly = true)
    public DiaryResponse getDiary(User user, Long diaryId) {
        Diary diary = findDiaryOrThrow(diaryId);
        validateDiaryOwner(user, diary);

        return convertToDiaryResponse(diary);
    }

    // 사용자의 모든 일기 조회 (페이징)
    @Transactional(readOnly = true)
    public DiaryPageResponse getMyDiaries(User user, int page, int size) {
        validateUser(user);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Diary> diaryPage = diaryRepository.findByUser(user, pageable);

        return convertToDiaryPageResponse(diaryPage);
    }

    // 감정별 일기 조회 (페이징)
    @Transactional(readOnly = true)
    public DiaryPageResponse getDiariesByEmotion(User user, Emotion emotion, int page, int size) {
        validateUser(user);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Diary> diaryPage = diaryRepository.findByEmotion(emotion, pageable);

        List<Diary> userDiaries = diaryPage.getContent().stream()
                .filter(diary -> diary.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        Page<Diary> filteredPage = new PageImpl<>(userDiaries, pageable, userDiaries.size());

        return convertToDiaryPageResponse(filteredPage);
    }

    // ==========================  예외 처리 메서드  ==========================

    private Diary findDiaryOrThrow(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
    }

    private void validateDiaryOwner(User user, Diary diary) {
        if (!diary.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_USER);
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    // ==========================  DTO 변환 메서드  ==========================

    private DiaryResponse convertToDiaryResponse(Diary diary) {
        return DiaryResponse.builder()
                .diaryId(diary.getId())
                .email(diary.getUser().getEmail())
                .emotion(diary.getEmotion())
                .content(diary.getContent())
                .imageUrls(diary.getImageUrls())
                .build();
    }

    private DiaryPageResponse convertToDiaryPageResponse(Page<Diary> diaryPage) {
        return DiaryPageResponse.builder()
                .content(diaryPage.getContent().stream()
                        .map(this::convertToDiarySummaryResponse)
                        .collect(Collectors.toList()))
                .currentPage(diaryPage.getNumber())
                .totalPages(diaryPage.getTotalPages())
                .totalElements(diaryPage.getTotalElements())
                .last(diaryPage.isLast())
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
