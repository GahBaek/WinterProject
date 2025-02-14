package com.example.winterdeom.domain.todolist.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoListReq {
    private LocalDate date;
}
