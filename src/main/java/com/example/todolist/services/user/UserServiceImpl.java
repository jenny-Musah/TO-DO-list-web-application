package com.example.todolist.services.user;

import com.example.todolist.data.dto.requests.UserLoginRequest;
import com.example.todolist.data.dto.requests.UserSignupRequest;
import com.example.todolist.data.dto.response.Response;
import com.example.todolist.data.models.User;
import com.example.todolist.data.repositories.UserRepository;
import com.example.todolist.utils.Validate;
import com.example.todolist.utils.exceptions.InvalidDetails;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;

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

    private User createUser(UserSignupRequest userSignupRequest){
        User user = new User();
        user.setEmailAddress(userSignupRequest.getEmail());
        user.setPassword(BCrypt.hashpw(userSignupRequest.getPassword(),BCrypt.gensalt()));
        return user;
    }

}
