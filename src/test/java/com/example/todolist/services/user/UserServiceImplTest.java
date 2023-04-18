package com.example.todolist.services.user;

import com.example.todolist.data.dto.requests.UserLoginRequest;
import com.example.todolist.data.dto.requests.UserSignupRequest;
import com.example.todolist.utils.exceptions.InvalidDetails;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
 class UserServiceImplTest {
    @Autowired private UserService userService;

    @Test
     public void testThatUserCanSignUp() {
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah99@gmail.com", "Jennnylkusgsy@34527");
        assertEquals("Sign up Successful", userService.signup(userSignupRequest).getMessage());
    }
    @Test
    public void testThatUserCanNotSignupTwoTimes(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah99@gmail.com", "Jennnylkusgsy@34527");
        userService.signup(userSignupRequest);
        assertEquals("User with this email already exist",userService.signup(userSignupRequest).getMessage());
    }
    @Test
    public void testThatUserCanNotSignupWithWrongPasswordFormat(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah99@gmail.com", "Jenny");
        assertThrows(InvalidDetails.class, () -> userService.signup(userSignupRequest));
    }
    @Test
    public void testThatUserCanNotSignupWithWrongEmailFormat(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jenny.com", "Jennnylkusgsy@34527");
        assertThrows(InvalidDetails.class, () -> userService.signup(userSignupRequest));
    }
    @Test
    public void testThatUserCanLogin(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah99@gmail.com", "Jennnylkusgsy@34527");
        userService.signup(userSignupRequest);
        UserLoginRequest userLoginRequest = new UserLoginRequest("jennymusah99@gmail.com", "Jennnylkusgsy@34527");
        assertEquals("Logged in successfully", userService.login(userLoginRequest).getMessage());
    }
  @Test
    public void testThatUserCanNotLoginWithIncorrectPassword(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah99@gmail.com", "Jennnylkusgsy@34527");
        userService.signup(userSignupRequest);
        UserLoginRequest userLoginRequest = new UserLoginRequest("jennymusah99@gmail.com", "Jennny");
        assertThrows(InvalidDetails.class,() -> userService.login(userLoginRequest));
    }
    @Test
    public void testThatUserCanNotLoginWithIncorrectEmail(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah99@gmail.com", "Jennnylkusgsy@34527");
        userService.signup(userSignupRequest);
        UserLoginRequest userLoginRequest = new UserLoginRequest("jennymusah99.com", "Jennnylkusgsy@34527");
        assertThrows(InvalidDetails.class,() -> userService.login(userLoginRequest));
    }


}
