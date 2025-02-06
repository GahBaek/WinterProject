package com.example.winterdeom.domain.diary.dto.res;

import com.example.winterdeom.domain.diary.domain.Emotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryPageResponse {
    private List<DiarySummaryResponse> content; // 개별 일기 요약 리스트
    private int currentPage;   // 현재 페이지 번호
    private int totalPages;    // 전체 페이지 수
    private long totalElements; // 전체 일기 개수
    private boolean last;      // 마지막 페이지 여부
}
