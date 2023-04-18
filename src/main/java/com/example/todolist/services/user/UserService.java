package com.example.todolist.services.user;

import com.example.todolist.data.dto.requests.UserLoginRequest;
import com.example.todolist.data.dto.requests.UserSignupRequest;
import com.example.todolist.data.dto.response.Response;

public interface UserService {

    Response signup(UserSignupRequest userSignupRequest);

    Response login(UserLoginRequest userLoginRequest);
}
