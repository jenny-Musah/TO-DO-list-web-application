package com.example.todolist.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String emailAddress;
    private String password;
   @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Todo> usersLists = new ArrayList<>();
}
