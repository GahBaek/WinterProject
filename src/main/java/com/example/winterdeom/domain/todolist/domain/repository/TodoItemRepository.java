package com.example.winterdeom.domain.todolist.domain.repository;

import com.example.winterdeom.domain.todolist.domain.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}
