package com.example.todolist.controller;

import com.example.todolist.data.dto.requests.UserLoginRequest;
import com.example.todolist.data.dto.requests.UserSignupRequest;
import com.example.todolist.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UserController {


    @Autowired private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest userSignupRequest){
        return new ResponseEntity<>(userService.signup(userSignupRequest), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest){
        return new ResponseEntity<>(userService.login(userLoginRequest), HttpStatus.OK);
    }
}
