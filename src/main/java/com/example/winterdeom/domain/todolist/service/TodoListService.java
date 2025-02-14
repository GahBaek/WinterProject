package com.example.winterdeom.domain.todolist.service;

import com.example.winterdeom.domain.todolist.domain.TodoItem;
import com.example.winterdeom.domain.todolist.domain.TodoList;
import com.example.winterdeom.domain.todolist.domain.repository.TodoItemRepository;
import com.example.winterdeom.domain.todolist.domain.repository.TodoListRepository;
import com.example.winterdeom.domain.todolist.dto.request.TodoItemReq;
import com.example.winterdeom.domain.todolist.dto.request.TodoItemUpdateReq;
import com.example.winterdeom.domain.todolist.dto.request.TodoListReq;
import com.example.winterdeom.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final TodoItemRepository todoItemRepository;

    // 투두리스트 생성
    @Transactional
    public Long createTodoList(User user, TodoListReq todoListReq) {

        TodoList todoList = TodoList.builder()
                .user(user)
                .date(todoListReq.getDate())
                .build();

        todoListRepository.save(todoList);

        return todoList.getId();
    }

    // 할 일 추가
    @Transactional
    public void addTodoItem(User user, TodoItemReq todoItemReq) {
        TodoList todoList = todoListRepository.findById(todoItemReq.getTodoListId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 투두리스트를 찾을 수 없습니다."));

        TodoItem todoItem = TodoItem.builder()
                .todoList(todoList)
                .title(todoItemReq.getTitle())
                .completed(false) // 처음 생성할 때는 false
                .build();

        todoItemRepository.save(todoItem);
    }

    // 할 일 수정
    @Transactional
    public void updateTodoItem(User user, Long todoItemId, TodoItemUpdateReq todoItemUpdateReq) {
        TodoItem todoItem = todoItemRepository.findById(todoItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 할 일을 찾을 수 없습니다."));

        todoItem.update(todoItemUpdateReq.getTitle(), todoItemUpdateReq.getCompleted());

        todoItemRepository.save(todoItem);

    }

}
