package com.example.winterdeom.domain.todolist.domain.repository;

import com.example.winterdeom.domain.todolist.domain.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
