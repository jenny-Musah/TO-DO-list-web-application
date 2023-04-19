package com.example.todolist.services.todo;

import com.example.todolist.data.dto.requests.CreateListRequest;
import com.example.todolist.data.dto.requests.SearchRequest;
import com.example.todolist.data.dto.requests.UserSignupRequest;
import com.example.todolist.data.dto.response.Response;
import com.example.todolist.data.dto.response.ViewToDoListResponse;
import com.example.todolist.services.user.UserService;
import com.example.todolist.utils.exceptions.InvalidDetails;
import jakarta.transaction.Transactional;
import jdk.jfr.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

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
    @Test
    public void testThatUserCanViewAllList(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        todoService.createList(createListRequest,response.getId());
        todoService.createList(createListRequest,response.getId());
        assertEquals(2, todoService.viewList(response.getId()).size());
    }
    @Test
    public void testThatEmptyListIsReturnedWhenNoTodoListAsBeenAdded(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        List<ViewToDoListResponse> lists = new ArrayList<>();
        assertTrue(todoService.viewList(response.getId()).isEmpty());

    }
    @Test
    public void testThatUserCanUpdateTodoList(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        CreateListRequest updateListRequest = new CreateListRequest("Wednesday food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        assertEquals("Updated successfully", todoService.updateList(createListRequest, response1.getId()).getMessage());
    }
     @Test
     @Name("Test that user can update only list name")
    public void testThatUserCanOnlyUpdateFieldTheyWant(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        CreateListRequest updateListRequest = new CreateListRequest("YummyYummy",
                "","",
                "");
        assertEquals("Updated successfully", todoService.updateList(createListRequest, response1.getId()).getMessage());
    }
     @Test
     @Name("Test that user can update only date")
    public void testThatUserCanOnlyUpdateFieldTheyWantToUpdate(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        CreateListRequest updateListRequest = new CreateListRequest("",
                "","12/09/2023",
                "");
        assertEquals("Updated successfully", todoService.updateList(createListRequest, response1.getId()).getMessage());
    }
    @Test
    public void testThatUserCanViewASingleList(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        ViewToDoListResponse viewToDoListResponse = new ViewToDoListResponse();
        viewToDoListResponse.setListName(createListRequest.getListName());
        viewToDoListResponse.setPriority(createListRequest.getPriority().toUpperCase());
        viewToDoListResponse.setDescription(createListRequest.getDescription());
        viewToDoListResponse.setDueDate("2023-06-04");
        assertEquals(viewToDoListResponse, todoService.viewToDo(response1.getId()));
    }
    @Test
    public void testThatUserCanViewASingleListAfterUpdate(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        CreateListRequest updateListRequest = new CreateListRequest("YummyYummy",
                "","",
                "");
        todoService.updateList(updateListRequest, response1.getId());
        ViewToDoListResponse viewToDoListResponse = new ViewToDoListResponse();
        viewToDoListResponse.setListName(updateListRequest.getListName());
        viewToDoListResponse.setPriority(createListRequest.getPriority().toUpperCase());
        viewToDoListResponse.setDescription(createListRequest.getDescription());
        viewToDoListResponse.setDueDate("2023-06-04");
        assertEquals(viewToDoListResponse, todoService.viewToDo(response1.getId()));
    }

    @Test
    public void testThatUserCanSearchForListWithListName(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        ViewToDoListResponse viewToDoListResponse = new ViewToDoListResponse();
        viewToDoListResponse.setListName(createListRequest.getListName());
        viewToDoListResponse.setPriority(createListRequest.getPriority().toUpperCase());
        viewToDoListResponse.setDescription(createListRequest.getDescription());
        viewToDoListResponse.setDueDate("2023-06-04");
        SearchRequest searchRequest = new SearchRequest(createListRequest.getListName());
        assertEquals(1, todoService.searchForTodoList(searchRequest,response.getId()).size());
    }
    @Test
    public void testThatUserCanSearchForListWithDescription(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        SearchRequest searchRequest = new SearchRequest(createListRequest.getDescription());
        assertEquals(1, todoService.searchForTodoList(searchRequest,response.getId()).size());
    }
    @Test
    public void testThatUserCanSearchForListWithSomePartOfDescription(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        ViewToDoListResponse viewToDoListResponse = new ViewToDoListResponse();
        viewToDoListResponse.setListName(createListRequest.getListName());
        viewToDoListResponse.setPriority(createListRequest.getPriority().toUpperCase());
        viewToDoListResponse.setDescription(createListRequest.getDescription());
        viewToDoListResponse.setDueDate("2023-06-04");
        SearchRequest searchRequest = new SearchRequest("Cook rice, eat spicy chicken");
        assertEquals(1, todoService.searchForTodoList(searchRequest,response.getId()).size());
    }
    @Test
    public void testThatTheSizeOfTheSearchListWhenWrongDetailsAreUsedToSearchIsZero(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        ViewToDoListResponse viewToDoListResponse = new ViewToDoListResponse();
        viewToDoListResponse.setListName(createListRequest.getListName());
        viewToDoListResponse.setPriority(createListRequest.getPriority().toUpperCase());
        viewToDoListResponse.setDescription(createListRequest.getDescription());
        viewToDoListResponse.setDueDate("2023-06-04");
        SearchRequest searchRequest = new SearchRequest(" i am jenny");
        assertEquals(0, todoService.searchForTodoList(searchRequest,response.getId()).size());
    }
    @Test
    public void testThatUserCanDeleteAllList(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        CreateListRequest createListRequest2 = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
            todoService.createList(createListRequest2,response.getId());
            assertEquals(2,todoService.viewList(response.getId()).size());
            todoService.deleteList(response.getId());
            assertEquals(0, todoService.viewList(response.getId()).size());

    }
    @Test
    public void testThatUserCanDeleteASingleTodo(){
        UserSignupRequest userSignupRequest = new UserSignupRequest("jennymusah90@gmail.com", "didiTinka673@89");
        Response response = userService.signup(userSignupRequest);
        CreateListRequest createListRequest = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        Response response1 = todoService.createList(createListRequest,response.getId());
        CreateListRequest createListRequest2 = new CreateListRequest("Mondays food list",
                "Cook rice, eat spicy chicken, fry chicken","04/06/2023",
                "high");
        todoService.createList(createListRequest2,response.getId());
        assertEquals(2,todoService.viewList(response.getId()).size());
        todoService.deleteTodo(response1.getId());
        assertEquals(1, todoService.viewList(response.getId()).size());
    }

}
