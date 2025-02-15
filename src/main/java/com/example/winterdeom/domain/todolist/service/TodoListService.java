package com.example.winterdeom.domain.todolist.service;

import com.example.winterdeom.domain.todolist.domain.TodoItem;
import com.example.winterdeom.domain.todolist.domain.TodoList;
import com.example.winterdeom.domain.todolist.domain.repository.TodoItemRepository;
import com.example.winterdeom.domain.todolist.domain.repository.TodoListRepository;
import com.example.winterdeom.domain.todolist.dto.request.TodoItemReq;
import com.example.winterdeom.domain.todolist.dto.request.TodoItemUpdateReq;
import com.example.winterdeom.domain.todolist.dto.request.TodoListReq;
import com.example.winterdeom.domain.todolist.dto.response.TodoItemResponse;
import com.example.winterdeom.domain.todolist.dto.response.TodoListResponse;
import com.example.winterdeom.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        TodoList todoList = findTodoListById(todoItemReq.getTodoListId());

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
        TodoItem todoItem = findTodoItemById(todoItemId);
        validateTodoItemOwnership(user, todoItem);
        todoItem.update(todoItemUpdateReq.getTitle(), todoItemUpdateReq.getCompleted());
        todoItemRepository.save(todoItem);
    }

    // 특정 날짜의 투두리스트 조회
    public List<TodoListResponse> getTodoListsByDate(User user, LocalDate date) {
        List<TodoList> todoLists = todoListRepository.findByUserAndDate(user, date);
        return convertToTodoListResponse(todoLists);
    }

    // 할 일 완료 상태 변경
    @Transactional
    public void toggleTodoItemStatus(User user, Long todoItemId) {
        TodoItem todoItem = findTodoItemById(todoItemId);
        validateTodoItemOwnership(user, todoItem);
        todoItem.update(todoItem.getTitle(), !todoItem.getCompleted());
        todoItemRepository.save(todoItem);
    }

    // 특정 할 일 삭제
    @Transactional
    public void deleteTodoItem(User user, Long todoItemId) {
        TodoItem todoItem = findTodoItemById(todoItemId);
        validateTodoItemOwnership(user, todoItem);
        todoItemRepository.delete(todoItem);
    }

    // 투두리스트 삭제(연관된 할 일도 같이 삭제됨)
    @Transactional
    public void deleteTodoList(User user, Long todoListId) {
        TodoList todoList = findTodoListById(todoListId);
        validateTodoListOwnership(user, todoList);
        todoListRepository.delete(todoList);
    }

    // ========================== 특정 ID로 조회하는 메서드  ==========================

    // 특정 ID의 할 일 조회
    private TodoItem findTodoItemById(Long todoItemId) {
        return todoItemRepository.findById(todoItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 할 일을 찾을 수 없습니다."));
    }

    // 특정 ID의 투두리스트 조회
    private TodoList findTodoListById(Long todoListID){
        return todoListRepository.findById(todoListID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 투두리스트를 찾을 수 없습니다."));
    }

    // ============================  본인 확인 메서드  ================================

    // 특정 ID의 할 일 조회 (본인 확인 로직 추가)
    private void validateTodoItemOwnership(User user, TodoItem todoItem) {
        if (!todoItem.getTodoList().getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 할 일만 수정/삭제할 수 있습니다.");
        }
    }

    // 특정 ID의 투두리스트 조회 (본인 확인 로직 추가)
    private void validateTodoListOwnership(User user, TodoList todoList) {
        if (!todoList.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 투두리스트만 수정/삭제할 수 있습니다.");
        }
    }

    // ==========================  DTO 변환 메서드  ==========================

    // 투두리스트 엔티티 리스트를 DTO리스트로 변환
    private List<TodoListResponse> convertToTodoListResponse(List<TodoList> todoLists) {
        return todoLists.stream()
                .map(todoList -> TodoListResponse.builder()
                        .id(todoList.getId())
                        .date(todoList.getDate())
                        .items(convertToTodoItemResponse(todoList))
                        .build()
                )
                .collect(Collectors.toList());
    }

    // 개별 투두리스트 내의 할 일 목록을 DTO리스트로 변환
    private List<TodoItemResponse> convertToTodoItemResponse(TodoList todoList) {
        return todoList.getItems().stream()
                .map(todoItem -> TodoItemResponse.builder()
                        .id(todoItem.getId())
                        .title(todoItem.getTitle())
                        .completed(todoItem.getCompleted())
                        .build()
                )
                .collect(Collectors.toList());
    }


}
