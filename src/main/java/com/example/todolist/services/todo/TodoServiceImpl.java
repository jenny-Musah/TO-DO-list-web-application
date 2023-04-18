package com.example.todolist.services.todo;

import com.example.todolist.data.dto.requests.CreateListRequest;
import com.example.todolist.data.dto.response.Response;
import com.example.todolist.data.dto.response.ViewToDoListResponse;
import com.example.todolist.data.models.Priority;
import com.example.todolist.data.models.Todo;
import com.example.todolist.data.models.User;
import com.example.todolist.data.repositories.ListRepository;
import com.example.todolist.services.user.UserService;
import com.example.todolist.utils.Validate;
import com.example.todolist.utils.exceptions.InvalidDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired private ListRepository listRepository;
    @Autowired private UserService userService;

    @Override public Response createList(CreateListRequest createListRequest, long userId) {
        User savedUser = userService.findUser(userId);
        if(!Validate.isDateFormatValid(createListRequest.getDueDate()) || LocalDate.parse(createListRequest.getDueDate(),dateTimeFormatter).isBefore(LocalDate.now())) throw new InvalidDetails("Date format is invalid enter date in this format: \"dd/MM/yyyy\" ");
        Todo savedToDo = listRepository.save(createTodo(createListRequest,savedUser));
        userService.addNewToDoList(savedToDo, savedUser.getId());
        return new Response(savedToDo.getId(), "Todo list added successfully");
    }

    @Override public List<ViewToDoListResponse> viewList(long userId) {
        User user = userService.findUser(userId);
        List<ViewToDoListResponse> viewList = new ArrayList<>();
        for( Todo todo : user.getUsersLists()){
            viewList.add(createViewResponse(todo));
        }
        return viewList;
    }

    private ViewToDoListResponse createViewResponse(Todo todo ){
        ViewToDoListResponse viewToDoListResponse = new ViewToDoListResponse();
        viewToDoListResponse.setListName(todo.getListName());
        viewToDoListResponse.setLocalDate(String.valueOf(todo.getDueDate()));
        viewToDoListResponse.setDescription(todo.getDescription() );
        viewToDoListResponse.setPriority(String.valueOf(todo.getPriority()));
        viewToDoListResponse.setCompleted(todo.isCompleted());
        return viewToDoListResponse;
    }
    public Todo createTodo(CreateListRequest createListRequest, User user){
        Todo todo = new Todo();
        todo.setDescription(createListRequest.getDescription());
        todo.setPriority(Priority.valueOf(createListRequest.getPriority().toUpperCase()));
        todo.setListName(createListRequest.getListName());
        todo.setDueDate(LocalDate.parse(createListRequest.getDueDate(),dateTimeFormatter));
        todo.setUser(user);
        return listRepository.save(todo);
    }

}
