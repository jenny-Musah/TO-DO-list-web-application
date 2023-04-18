package com.example.todolist.data.repositories;

import com.example.todolist.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


    User findUserByEmailAddress(String email);

}
