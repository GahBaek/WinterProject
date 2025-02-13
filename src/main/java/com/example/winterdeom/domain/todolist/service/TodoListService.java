package com.example.winterdeom.domain.todolist.service;

import com.example.winterdeom.domain.todolist.domain.TodoList;
import com.example.winterdeom.domain.todolist.domain.repository.TodoListRepository;
import com.example.winterdeom.domain.todolist.dto.request.TodoListReq;
import com.example.winterdeom.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoListService {
    private final TodoListRepository todoListRepository;

    // 투두리스트 생성
    @Transactional
    public Long createTodoList(User user, TodoListReq todoListReq) {
        LocalDate date = LocalDate.parse(todoListReq.getDate());

        TodoList todoList = TodoList.builder()
                .user(user)
                .date(date)
                .build();

        todoListRepository.save(todoList);

        return todoList.getId();
    }
}
