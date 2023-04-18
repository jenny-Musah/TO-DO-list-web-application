package com.example.todolist.services.todo;

import com.example.todolist.data.dto.requests.CreateListRequest;
import com.example.todolist.data.dto.response.Response;
import com.example.todolist.data.dto.response.ViewToDoListResponse;
import java.util.List;

public interface TodoService {

    Response createList(CreateListRequest createListRequest, long userId);

    List<ViewToDoListResponse> viewList(long userId);

    Response updateList(CreateListRequest createListRequest,long listId);
}
