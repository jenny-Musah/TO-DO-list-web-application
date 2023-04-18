package com.example.todolist.services.user;

import com.example.todolist.data.dto.requests.UserLoginRequest;
import com.example.todolist.data.dto.requests.UserSignupRequest;
import com.example.todolist.data.dto.response.Response;
import com.example.todolist.data.models.Todo;
import com.example.todolist.data.models.User;

public interface UserService {

    User findUser(long userId);

    Response signup(UserSignupRequest userSignupRequest);

    Response login(UserLoginRequest userLoginRequest);

    void addNewToDoList(Todo savedToDo, long id);
}
