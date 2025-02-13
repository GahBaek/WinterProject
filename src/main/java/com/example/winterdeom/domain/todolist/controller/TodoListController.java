package com.example.winterdeom.domain.todolist.controller;

import com.example.winterdeom.domain.todolist.dto.request.TodoListReq;
import com.example.winterdeom.domain.todolist.service.TodoListService;
import com.example.winterdeom.domain.user.domain.User;
import com.example.winterdeom.global.auth.AuthenticatedUser;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/todolist")
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;

    @PostMapping
    public ResponseEntity<?> createTodoList(
            @AuthenticatedUser User user,
            @RequestBody TodoListReq todoListReq
    ){
        Long todoListId = todoListService.createTodoList(user, todoListReq);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "투두리스트를 정상적으로 생성되었습니다.",
                        "todoListId", todoListId
                ));
    }


}
