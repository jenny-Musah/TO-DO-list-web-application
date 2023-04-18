package com.example.todolist.services.todo;

import com.example.todolist.data.dto.requests.CreateListRequest;
import com.example.todolist.data.dto.response.Response;

public interface TodoService {

    Response createList(CreateListRequest createListRequest, long userId);
}
