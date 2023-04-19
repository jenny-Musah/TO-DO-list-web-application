package com.example.todolist.controller;

import com.example.todolist.data.dto.requests.CreateListRequest;
import com.example.todolist.data.dto.requests.SearchRequest;
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
      @GetMapping ("/{userId}")
    public ResponseEntity<?> viewList(@PathVariable long userId){
        return new ResponseEntity<>(todoService.viewList(userId), HttpStatus.OK);
    }

    @PostMapping("/update/{listId}")
    public ResponseEntity<?> updateList(@RequestBody CreateListRequest createListRequest, @PathVariable long listId){
        return new ResponseEntity<>(todoService.updateList(createListRequest,listId), HttpStatus.OK);
    }

    @GetMapping("/todo/{listId}")
    public ResponseEntity<?> viewTodo(@PathVariable long listId){
        return new ResponseEntity<>(todoService.viewToDo(listId), HttpStatus.OK);
    }
    @PostMapping("/search/{userId}")
    public ResponseEntity<?> search(@RequestBody SearchRequest searchRequest, @PathVariable long userId){
        return new ResponseEntity<>(todoService.searchForTodoList(searchRequest,userId), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteList(@PathVariable long userId){
        return new ResponseEntity<>(todoService.deleteList(userId), HttpStatus.OK);
    }
    @DeleteMapping("/delete/todo/{listId}")
    public ResponseEntity<?> deleteTodo(@PathVariable long listId){
        return new ResponseEntity<>(todoService.deleteTodo(listId), HttpStatus.OK);
    }

}
