package com.example.winterdeom.domain.todolist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(title = "TodoListReq : 투두리스트 날짜 요청 객체", description = "POST:/api/v1/todolist에서 사용합니다.")
public class TodoListReq {
    @Schema(description = "투두리스트 날짜", example = "2024-02-14")
    private LocalDate date;
}
