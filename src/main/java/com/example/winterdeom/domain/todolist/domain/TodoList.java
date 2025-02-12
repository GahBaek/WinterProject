package com.example.winterdeom.domain.todolist.domain;

import com.example.winterdeom.domain.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "todo_lists")
@Getter
@NoArgsConstructor
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;  // 해당 투두리스트의 날짜

//    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TodoItem> items;  // 해당 날짜의 할 일 목록
}
