package com.example.todolist.services.user;

import com.example.todolist.data.dto.requests.UserLoginRequest;
import com.example.todolist.data.dto.requests.UserSignupRequest;
import com.example.todolist.data.dto.response.Response;
import com.example.todolist.data.dto.response.ViewToDoListResponse;
import com.example.todolist.data.models.Todo;
import com.example.todolist.data.models.User;
import com.example.todolist.data.repositories.UserRepository;
import com.example.todolist.services.mailService.MailService;
import com.example.todolist.utils.Validate;
import com.example.todolist.utils.exceptions.InvalidDetails;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;

    @Autowired private MailService mailService;
    @Override public User findUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new InvalidDetails("User is not registered..."));
    }

    @Override public Response signup(UserSignupRequest userSignupRequest) {
        if(!Validate.isEmailValid(userSignupRequest.getEmail()) || !Validate.isPasswordValid(userSignupRequest.getPassword())) throw new InvalidDetails("Invalid details");
        User user = userRepository.findUserByEmailAddress(userSignupRequest.getEmail());
        if(user != null) return new Response(0, "User with this email already exist");
        User savedUser = userRepository.save(createUser(userSignupRequest));
        return new Response(savedUser.getId(), "Sign up Successful");
    }

    @Override public Response login(UserLoginRequest userLoginRequest) {
        User savedUser = userRepository.findUserByEmailAddress(userLoginRequest.getEmail());
        if(savedUser == null || !BCrypt.checkpw(userLoginRequest.getPassword(),savedUser.getPassword())) throw new InvalidDetails("Invalid login details");
        return new Response(savedUser.getId(),"Logged in successfully");
    }

    @Override public void addNewToDoList(Todo savedToDo, long id) {
        User savedUser = userRepository.findById(id).orElseThrow(() -> new InvalidDetails("User is not registered yet..."));
        savedUser.getUsersLists().add(savedToDo);
        userRepository.save(savedUser);
    }

    @Override public void updateLists(Todo todo) {
        User user  = findUser(todo.getUser().getId());
        for(Todo todo1: user.getUsersLists()){
            if(todo1.getId() == todo.getId()){
                user.getUsersLists().remove(todo1);
                user.getUsersLists().add(todo);
                userRepository.save(user);
                break;
            }
        }
    }

    @Override public void deleteList(long userId) {
        User user = findUser(userId);
        user.getUsersLists().clear();
        userRepository.save(user);
    }

    @Override public void deleteTodo(Todo todo) {
        User user = findUser(todo.getUser().getId());
        user.getUsersLists().remove(todo);
        userRepository.save(user);
    }

    private User createUser(UserSignupRequest userSignupRequest){
        User user = new User();
        user.setEmailAddress(userSignupRequest.getEmail());
        user.setPassword(BCrypt.hashpw(userSignupRequest.getPassword(),BCrypt.gensalt()));
        return user;
    }
    private ViewToDoListResponse createViewResponse(Todo todo ){
        ViewToDoListResponse viewToDoListResponse = new ViewToDoListResponse();
        viewToDoListResponse.setListName(todo.getListName());
        viewToDoListResponse.setDueDate(String.valueOf(todo.getDueDate()));
        viewToDoListResponse.setDescription(todo.getDescription() );
        viewToDoListResponse.setPriority(String.valueOf(todo.getPriority()));
        return viewToDoListResponse;
    }

    //@Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    @Scheduled(fixedDelay = 120000)
    public void reminder(){
        List<ViewToDoListResponse> notExpiredTodo = new ArrayList<>();
        for(User user: userRepository.findAll()){
            for(Todo todo : user.getUsersLists()){
                if(todo.getDueDate().isAfter(LocalDate.now())){
                    notExpiredTodo.add(createViewResponse(todo));
                }
            }
            if(notExpiredTodo == null)mailService.send(user.getEmailAddress(),notExpiredTodo.toString(),"Daily Reminder");
        }

    }

}
