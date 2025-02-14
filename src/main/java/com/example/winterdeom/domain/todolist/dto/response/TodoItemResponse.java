package com.example.winterdeom.domain.todolist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "TodoItemResponse : 투두항목 응답 객체", description = "GET:/api/v1/todolist에서 사용합니다.")
public class TodoItemResponse {
    @Schema(description = "할 일 ID", example = "10")
    private Long id;

    @Schema(description = "할 일 제목", example = "운동하기")
    private String title;

    @Schema(description = "완료 여부", example = "false")
    private Boolean completed;
}
