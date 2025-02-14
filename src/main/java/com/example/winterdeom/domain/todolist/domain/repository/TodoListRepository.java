package com.example.winterdeom.domain.todolist.domain.repository;

import com.example.winterdeom.domain.todolist.domain.TodoList;
import com.example.winterdeom.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    // 사용자가 작성한 날짜의 투두리스트 가져오기
    List<TodoList> findByUserAndDate(User user, LocalDate date);
}
