package com.example.todolist.controller;

import com.example.todolist.data.dto.requests.CreateListRequest;
import com.example.todolist.services.todo.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/list")
public class ListController {

    @Autowired private TodoService todoService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createList(@RequestBody CreateListRequest createListRequest, @PathVariable long userId){
        return new ResponseEntity<>(todoService.createList(createListRequest,userId), HttpStatus.OK);
    }

}
