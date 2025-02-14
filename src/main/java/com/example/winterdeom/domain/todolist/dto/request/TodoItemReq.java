package com.example.winterdeom.domain.todolist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "TodoItemReq : 할 일 요청 객체", description = "POST:/api/v1/todolist/item에서 사용합니다.")
public class TodoItemReq {
    @Schema(description = "할 일이 추가될 투두리스트 ID", example = "1")
    private Long todoListId;

    @Schema(description = "할 일 제목", example = "운동하기")
    private String title;
}
