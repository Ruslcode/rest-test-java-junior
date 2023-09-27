package com.clear.solutions.source.services;

import com.clear.solutions.source.entities.User;
import com.clear.solutions.source.entities.UserDTO;
import com.clear.solutions.source.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User("John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1));
        User user2 = new User("Alice", "Smith", "alice@example.com", LocalDate.of(1985, 5, 10));
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> userDTOs = userService.getAllUsers();

        assertEquals(2, userDTOs.size());
        assertEquals("John", userDTOs.get(0).getFirstName());
        assertEquals("Alice", userDTOs.get(1).getFirstName());
    }

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO("John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1));
        User user = new User("John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1));

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO createResponse = userService.createUser(userDTO);

        assertEquals("John", createResponse.getFirstName());
    }

    @Test
    public void testUpdateUser() {
        UserDTO updatedUserDTO = new UserDTO("Updated", "User", "updated@example.com", LocalDate.of(2000, 1, 1));
        User existingUser = new User("John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1));

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        UserDTO updateResponse = userService.updateUser(updatedUserDTO);

        assertEquals("Updated", updateResponse.getFirstName());
    }

    @Test
    public void testDeleteUser() {
        Long userID = 1L;
        User user = new User("John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1));

        when(userRepository.findById(userID)).thenReturn(Optional.of(user));

        userService.deleteUser(userID);

        verify(userRepository, times(1)).delete(user);
    }
}
