package com.example.winterdeom.domain.todolist.controller;

import com.example.winterdeom.domain.common.ResponseDto;
import com.example.winterdeom.domain.todolist.dto.request.TodoItemReq;
import com.example.winterdeom.domain.todolist.dto.request.TodoItemUpdateReq;
import com.example.winterdeom.domain.todolist.dto.request.TodoListReq;
import com.example.winterdeom.domain.todolist.dto.response.TodoListResponse;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    public ResponseEntity<ResponseDto<Long>> createTodoList(
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
    public ResponseEntity<ResponseDto<Void>> addTodoItem(
            @AuthenticatedUser User user,
            @Parameter(description = "투두리스트에 추가할 할 일을 입력해주세요. Schemas의 TodoItemReq를 참고해주세요.", required = true) @RequestBody TodoItemReq todoItemReq
    ) {
        todoListService.addTodoItem(user, todoItemReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.res(HttpStatus.CREATED, "할 일이 정상적으로 추가되었습니다."));
    }

    @Operation(summary = "할 일 수정", description = "특정 할 일을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "할 일이 정상적으로 수정되었습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 할 일입니다.", content = @Content)
    })
    @PutMapping("/item/{todoItemId}")
    public ResponseEntity<ResponseDto<Void>> updateTodoItem(
            @AuthenticatedUser User user,
            @Parameter(description = "수정할 투두 항목 아이디를 입력해주세요", required = true) @PathVariable Long todoItemId,
            @Parameter(description = "수정할 투두항목을 입력해주세요. Schemas의 TodoItemUpdateReq를 참고해주세요.", required = true) @RequestBody TodoItemUpdateReq todoItemUpdateReq
    ) {
        todoListService.updateTodoItem(user, todoItemId, todoItemUpdateReq);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "할 일이 정상적으로 수정되었습니다."));
    }

    @Operation(summary = "특정 날짜의 투두리스트 조회", description = "특정 날짜의 투두리스트 및 할 일 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투두리스트 조회에 성공하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoListResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 날짜 형식입니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<TodoListResponse>> getTodoListsByDate(
            @AuthenticatedUser User user,
            @RequestParam("date") LocalDate date
    ) {
        List<TodoListResponse> todoLists = todoListService.getTodoListsByDate(user, date);
        return ResponseEntity.ok(todoLists);
    }

    @Operation(summary = "할 일 완료 상태 변경", description = "특정 할 일의 완료 상태를 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "할 일이 상태가 변경되었습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 할 일입니다.", content = @Content)
    })
    @PatchMapping("/item/{todoItemId}/status")
    public ResponseEntity<ResponseDto<Void>> toggleTodoItemStatus(
            @AuthenticatedUser User user,
            @Parameter(description = "상태 변경할 투두 항목 아이디를 입력해주세요", required = true) @PathVariable Long todoItemId
    ) {
        todoListService.toggleTodoItemStatus(user, todoItemId);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "할 일이 상태가 정상적으로 변경되었습니다."));
    }

    @Operation(summary = "특정 할 일 삭제", description = "할 일을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "할 일이 수정되었습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 할 일입니다.", content = @Content)
    })
    @DeleteMapping("/item/{todoItemId}")
    public ResponseEntity<ResponseDto<Void>> deleteTodoItem(
            @AuthenticatedUser User user,
            @Parameter(description = "삭제할 투두 항목 아이디를 입력해주세요", required = true) @PathVariable Long todoItemId
    ) {
        todoListService.deleteTodoItem(user, todoItemId);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "할 일이 정상적으로 삭제되었습니다."));
    }

    @Operation(summary = "투두리스트 삭제", description = "투두리스트를 삭제하면, 연관된 할 일도 모두 삭제됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투두리스트가 삭제되었습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 투두리스트입니다.", content = @Content)
    })
    @DeleteMapping("/{todoListId}")
    public ResponseEntity<ResponseDto<Void>> deleteTodoList(
            @AuthenticatedUser User user,
            @Parameter(description = "삭제할 투두리스트 아이디를 입력해주세요", required = true) @PathVariable Long todoListId
    ) {
        todoListService.deleteTodoList(user, todoListId);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "투두리스트가 정상적으로 삭제되었습니다."));
    }

}
