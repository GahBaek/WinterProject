package com.example.winterdeom.domain.diary.dto.res;

import com.example.winterdeom.domain.diary.domain.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "일기 페이지 응답 DTO")
public class DiaryPageResponse {
    @Schema(description = "일기 목록")
    private List<DiarySummaryResponse> content;

    @Schema(description = "현재 페이지 번호", example = "0")
    private int currentPage;

    @Schema(description = "전체 페이지 수", example = "10")
    private int totalPages;

    @Schema(description = "전체 일기 개수", example = "100")
    private long totalElements;

    @Schema(description = "마지막 페이지 여부", example = "false")
    private boolean last;
}