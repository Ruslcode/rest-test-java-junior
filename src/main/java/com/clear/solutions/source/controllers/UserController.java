package com.clear.solutions.source.controllers;

import com.clear.solutions.source.entities.UserDTO;
import com.clear.solutions.source.exceptions.InvalidDataException;
import com.clear.solutions.source.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @Value("${number.years.allowed.for.registration}")
    private int numberOfYears;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {
        if (!user.getEmail().contains("@gmail.com")) {
            throw new InvalidDataException("Invalid email format!");
        }
        if (user.getBirthdate().isAfter(LocalDate.now()) || user.getBirthdate().until(LocalDate.now()).getYears() < numberOfYears) {
            throw new InvalidDataException("Birthdate cannot be in the future or user have less 18 y. o.!");
        }
        UserDTO createResponse = userService.createUser(user);
        return new ResponseEntity<>(createResponse, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam LocalDate start,
                                                     @RequestParam LocalDate end) {
        if (start.isAfter(end)) {
            throw new InvalidDataException("date start is after date and !!!!");
        }
        List<UserDTO> users = userService.searchUsers(start, end);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        if (!user.getEmail().contains("@gmail.com")) {
            throw new InvalidDataException("Invalid email format!");
        }
        if (user.getBirthdate().isAfter(LocalDate.now())) {
            throw new InvalidDataException("Birthdate cannot be in the future!");
        }
        UserDTO updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userID) {
        userService.deleteUser(userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidDataException(InvalidDataException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
