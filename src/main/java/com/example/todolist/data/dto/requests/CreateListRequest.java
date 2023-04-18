package com.example.todolist.data.dto.requests;

import com.example.todolist.data.models.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateListRequest {
    private String listName;
    private String description;
    private String dueDate;
    private String priority;
}
