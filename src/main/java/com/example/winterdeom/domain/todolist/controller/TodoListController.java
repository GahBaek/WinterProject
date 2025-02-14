package com.example.winterdeom.domain.todolist.controller;

import com.example.winterdeom.domain.common.ResponseDto;
import com.example.winterdeom.domain.todolist.dto.request.TodoItemReq;
import com.example.winterdeom.domain.todolist.dto.request.TodoListReq;
import com.example.winterdeom.domain.todolist.service.TodoListService;
import com.example.winterdeom.domain.user.domain.User;
import com.example.winterdeom.global.auth.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@Tag(name = "투두리스트 API", description = "투두리스트 관련 API")
@RestController
@RequestMapping("/api/v1/todolist")
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;

    @Operation(summary = "투두리스트 생성", description = "새로운 투두리스트를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "투두리스트가 정상적으로 생성되었습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createTodoList(
            @AuthenticatedUser User user,
            @Parameter(description = "투두리스트 날짜를 입력해주세요. Schemas의 TodoListReq를 참고해주세요.", required = true) @RequestBody TodoListReq todoListReq
    ){
        Long todoListId = todoListService.createTodoList(user, todoListReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.res(HttpStatus.CREATED, "투두리스트가 정상적으로 생성되었습니다.", todoListId));
    }

    @Operation(summary = "할 일 추가", description = "투두리스트에 할 일을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "할 일이 정상적으로 추가되었습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 투두리스트입니다.", content = @Content)
    })
    @PostMapping("/item")
    public ResponseEntity<?> addTodoItem(
            @AuthenticatedUser User user,
            @Parameter(description = "투두리스트에 추가할 할 일을 입력해주세요. Schemas의 TodoItemReq를 참고해주세요.", required = true) @RequestBody TodoItemReq todoItemReq
    ) {
        todoListService.addTodoItem(user, todoItemReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.res(HttpStatus.CREATED, "할 일이 정상적으로 추가되었습니다."));
    }

}
