package com.example.todolist.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewToDoListResponse {
    private String listName;
    private String description;
    private String priority;
    private String dueDate;
}
