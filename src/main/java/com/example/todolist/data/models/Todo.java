package com.example.todolist.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Todo {
    @Id
    @GeneratedValue
    private long id;
    private String listName;
    private String description;
    private LocalDate dueDate;
    @Enumerated
    private Priority priority;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
