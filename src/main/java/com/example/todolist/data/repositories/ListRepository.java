package com.example.todolist.data.repositories;

import com.example.todolist.data.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<Todo,Long> {

}
