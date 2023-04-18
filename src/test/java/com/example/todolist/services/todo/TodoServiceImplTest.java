package com.example.todolist.services.todo;

import com.example.todolist.data.dto.requests.CreateListRequest;
import com.example.todolist.data.dto.requests.UserSignupRequest;
import com.example.todolist.data.dto.response.Response;
import com.example.todolist.services.user.UserService;
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
class TodoServiceImplTest {


    @Autowired TodoService todoService;

    @Autowired UserService userService;
    @Test
    public void testThatListCanCreated(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/08/2023",
                "high");
        assertEquals("Todo list added successfully", todoService.createList(createListRequest, response.getId()).getMessage());
    }
    @Test
    public void testThatListCanNotBeScheduledBehindTime(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/01/2023",
                "high");
        assertThrows(InvalidDetails.class, () -> todoService.createList(createListRequest, response.getId()));
    }

}
