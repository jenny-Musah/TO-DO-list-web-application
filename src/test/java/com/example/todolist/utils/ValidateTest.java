package com.example.todolist.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateTest {


    @Test
    public void testThatDateFormatIsCorrect(){
        assertTrue(Validate.isDateFormatValid("23/06/2023"));
    }
    @Test
    public void testThatWrongDateFormatCanNotBeSet(){
        assertFalse(Validate.isDateFormatValid("2345/78/2322"));
    }
    @Test
    public void testThatEmailFormatIsCorrect(){
        assertTrue(Validate.isEmailValid("jennymusah@gmail.com"));
    }
    @Test
    public void testThatWrongEmailFormatIsFalse(){
        assertFalse(Validate.isEmailValid("heyje.com@"));
    }
    @Test public void testThatPasswordFormatISCorrect(){
        assertTrue(Validate.isPasswordValid("Jenny@hdy246383"));
    }
    @Test
    public void testThatWrongPasswordFormatIsFalse(){
        assertFalse(Validate.isPasswordValid("jenny"));
    }

}
