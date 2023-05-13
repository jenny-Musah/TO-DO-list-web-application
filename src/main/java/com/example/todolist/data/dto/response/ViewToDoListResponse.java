package com.example.todolist.data.dto.response;

import lombok.*;

@Setter
@Getter
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
                """, listName, dueDate, priority, description);
    }
}
