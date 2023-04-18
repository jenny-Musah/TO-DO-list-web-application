# ToDo list web application
*This is a To-do list web application that help users with personal organization of daily activities, schedule planning and personal management. The application is built using Java Spring Boot, with a MySQL database . The project uses Git for version control.*

# Signup end-point
*This end-point creates a new user, it accepts a email address and password which is saved in the database and then returns the user id and a message.*
# Request
* Url : `localhost:8080/api/v1/users/signup`
* Method : POST
* Header : 
  * `Content-Type : application/json`
* Body: 
```
{
    "email" : "jennifermusah@gmail.com",
    "password" : "Jennifer@453"
}
```
* Fields:
    * `email`(required,String): *The email of the user*
    * `password`(required,String): *The password of the user*
# Response 1
*successful request.*
* Status code : `201 created`
* Body:
```
{
    "id": 1,
    "message": "Sign up Successful"
}
```
* Fields:
  * `id`(long): *The user id*
  * `message`(String): *Request message*
# Response 2
*unsuccessful request due to signing up with the same email times.*
* Status code : `200 ok`
* Body:
```
{
    "id": 0,
    "message": "User with this email already exist"
}
```
* Fields:
  * `id`(long): *The user id*
  * `message`(String): *Request message*
# Response 3
*unsuccessful request due to wrong email or password format.*
* Status code : `400 bad request`
* Body:
```
{
   Invalid details
}
```

# Login end-point
*This end-point is for authentication, it ensures only registered users can access applications end-points. It accepts the email address and password the user used in signing up it then checks if the email exist and also compares the hashed password in the database to the one the user entered if they match it returns the user id and a message.*
# Request
* Url : `localhost:8080/api/v1/users/login`
* Method : POST
* Header :
  * `Content-Type : application/json`
* Body:
```
{
    "email" : "jennifermusah@gmail.com",
    "password" : "Jennifer@453"
}
```
* Fields:
  * `email`(required,String): *The email of the user*
  * `password`(required,String): *The password of the user*
# Response 1
*successful request.*
* Status code : `200 ok`
* Body:
```
{
    "id": 1,
    "message": "Logged in successfully"
}
```
* Fields:
  * `id`(long): *The user id*
  * `message`(String): *Request message*
# Response 2
*unsuccessful request due to incorrect email or password*
* Status code : `400 bad request`
* Body:
```
{
   Invalid login details
}
```
# Create to-do list
*This end-point is for creating a new to-do list, the end-point accepts,list name, description,due date for list execution, priority of the list and returns the list id and a message.*
# Request
* Url : `localhost:8080/api/v1/list/create/{userId}`
* Method : POST
* Header :
  * `Content-Type : application/json`
* Parameter :
  * `userId : long`
* Body:
```
{
     "listName" : "Monday food list",
     "description" : "Rice, fish brook, yellow soup",
     "dueDate" : "20/04/2023",
     "priority" : "HIGH"
}
```
* Fields:
  * `listName`(required,String): *To-do list name*
  * `description`(required,String): *Tasks on the list*
  * `dueDate`(required,String): *Date at which task should be executed*
  * `priority`(required,String)*Priority of task*
# Response 1
*successful request.*
* Status code : `200 ok`
* Body:
```
{
    "id": 3,
    "message": "Todo list added successfully"
}
```
# Response 2
*unsuccessful request due to invalid date.*
* Status code : `400 bad request`
* Body:
```
{
    Date format is invalid enter date in this format: "dd/MM/yyyy" 
}
```
# View all lists
*This end-point allows user to view all existing to-do lists. It is a get request that returns all the user saved lists.*
# Request
* Url : `localhost:8080/api/v1/list/create/{userId}`
* Method : GET
* Header :
  * `Content-Type : application/json`
* Parameter : 
  * `userId : long` 
# Response
*successful request.*
* Status code : `200 ok`
* Body:
```
[
    {
        "listName": "Monday food list",
        "description": "Rice, fish brook, yellow soup",
        "localDate": "2023-06-20",
        "completed": false,
        "priority": "HIGH"
    },
    {
        "listName": "Monday food list",
        "description": "Rice, fish brook, yellow soup",
        "localDate": "2023-06-20",
        "completed": false,
        "priority": "HIGH"
    },
    {
        "listName": "Tuesday food list",
        "description": "French fries, fish brook, yellow soup",
        "localDate": "2023-05-01",
        "completed": false,
        "priority": "HIGH"
    }
]
```
