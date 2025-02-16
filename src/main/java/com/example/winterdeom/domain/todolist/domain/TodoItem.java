package com.example.winterdeom.domain.todolist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
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

    @ManyToOne(fetch = FetchType.LAZY) //LAZY로딩 적용
    @JoinColumn(name = "list_id", nullable = false)
    private TodoList todoList;  // 할 일이 속한 투두리스트

    @Column(nullable = false)
    private String title;  // 할 일 내용

    @Column(nullable = false)
    private Boolean completed = false;  // 완료 여부

    @Builder
    public TodoItem(TodoList todoList, String title, Boolean completed){
        this.todoList = todoList;
        this.title = title;
        this.completed = completed;
    }

    public void update(String title, Boolean completed) {
        this.title = title;
        this.completed = completed;
    }
}
