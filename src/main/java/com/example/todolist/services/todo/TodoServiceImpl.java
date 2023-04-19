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

    @Override
        public Response updateList(CreateListRequest createListRequest, long listId) {
        Todo todo = listRepository.findById(listId).orElseThrow(() -> new InvalidDetails("List id is incorrect"));
        if(createListRequest.getListName() != null && !createListRequest.getListName().isBlank() && !createListRequest.getListName().isEmpty())todo.setListName(createListRequest.getListName());
        if(createListRequest.getDescription() != null && !createListRequest.getDescription().isBlank() && !createListRequest.getDescription().isEmpty())todo.setDescription(createListRequest.getDescription());
        if(createListRequest.getDueDate() != null && !createListRequest.getDueDate().isBlank() && !createListRequest.getDueDate().isEmpty())todo.setDueDate(LocalDate.parse(createListRequest.getDueDate(),dateTimeFormatter));
        if(createListRequest.getPriority() != null && !createListRequest.getPriority().isBlank() && !createListRequest.getPriority().isEmpty())todo.setPriority(
                Priority.valueOf(createListRequest.getPriority().toUpperCase()));
        userService.updateLists(todo);
        listRepository.save(todo);
        return new Response(todo.getId(),"Updated successfully");
    }

    @Override public ViewToDoListResponse viewToDo(long id) {
        return createViewResponse(listRepository.findById(id).orElseThrow(() -> new InvalidDetails("List does not exist")));
    }

    private ViewToDoListResponse createViewResponse(Todo todo ){
        ViewToDoListResponse viewToDoListResponse = new ViewToDoListResponse();
        viewToDoListResponse.setListName(todo.getListName());
        viewToDoListResponse.setDueDate(String.valueOf(todo.getDueDate()));
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
