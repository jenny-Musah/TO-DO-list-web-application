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

    @Override
    public String toString(){
        return String.format("""
                List Name : %s%n
                Due date : %s%n
                Priority : %s
                Tasks : %s%n
                """, listName, description,priority, dueDate);
    }
}
