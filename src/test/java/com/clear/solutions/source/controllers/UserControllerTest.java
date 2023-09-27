package com.clear.solutions.source.controllers;

import com.clear.solutions.source.controllers.UserController;
import com.clear.solutions.source.entities.UserDTO;
import com.clear.solutions.source.services.UserService;
import com.clear.solutions.source.exceptions.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser_ValidData() {
        UserDTO userDTO = new UserDTO("John", "Doe", "john@gmail.com", LocalDate.of(1990, 1, 1));
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstName());
    }

    @Test
    public void testCreateUser_InvalidEmail() {
        UserDTO userDTO = new UserDTO("John", "Doe", "invalid_email", LocalDate.of(1990, 1, 1));

        try {
            userController.createUser(userDTO);
            fail("Should have thrown InvalidDataException");
        } catch (InvalidDataException e) {
            assertEquals("Invalid email format!", e.getMessage());
        }
    }

    @Test
    public void testCreateUser_FutureBirthdate() {
        UserDTO userDTO = new UserDTO("John", "Doe", "john@gmail.com", LocalDate.of(2100, 1, 1));

        try {
            userController.createUser(userDTO);
            fail("Should have thrown InvalidDataException");
        } catch (InvalidDataException e) {
            assertEquals("Birthdate cannot be in the future!", e.getMessage());
        }
    }

    @Test
    public void testGetAllUsers() {
        UserDTO user1 = new UserDTO("John", "Doe", "john@gmail.com", LocalDate.of(1990, 1, 1));
        UserDTO user2 = new UserDTO("Alice", "Smith", "alice@gmail.com", LocalDate.of(1985, 5, 10));
        List<UserDTO> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getFirstName());
        assertEquals("Alice", response.getBody().get(1).getFirstName());
    }
}