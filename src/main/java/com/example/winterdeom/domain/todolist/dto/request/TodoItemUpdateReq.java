package com.example.winterdeom.domain.todolist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "TodoItemUpdateReq : 수정할 할 일 요청 객체", description = "PUT:/api/v1/todolist/item/{todoItemId}에서 사용합니다.")
public class TodoItemUpdateReq {
    @Schema(description = "할 일 제목", example = "책 읽기")
    private String title;

    @Schema(description = "완료 여부", example = "true")
    private Boolean completed;
}
