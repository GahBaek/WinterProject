package com.example.winterdeom.domain.todolist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@Schema(title = "TodoListResponse : 투두리스트 응답 객체", description = "GET:/api/v1/todolist에서 사용합니다.")
public class TodoListResponse {
    @Schema(description = "투두리스트 ID", example = "1")
    private Long id;

    @Schema(description = "투두리스트 날짜", example = "2024-02-14")
    private LocalDate date;

    @Schema(description = "할 일 목록")
    private List<TodoItemResponse> items;
}
