package com.example.winterdeom.domain.todolist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todo_items")
@Getter
@NoArgsConstructor
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private TodoList todoList;  // 할 일이 속한 투두리스트

    @Column(nullable = false)
    private String title;  // 할 일 내용

    @Column(nullable = false)
    private Boolean completed = false;  // 완료 여부
}
