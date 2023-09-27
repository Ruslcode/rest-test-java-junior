package com.clear.solutions.source.services;

import com.clear.solutions.source.configuration.ModelMapperConfig;
import com.clear.solutions.source.entities.User;
import com.clear.solutions.source.entities.UserDTO;
import com.clear.solutions.source.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapperConfig modelMapper;

    public List<UserDTO> searchUsers(LocalDate dateStart, LocalDate dateEnd) {
        return userRepository.findByBirthdateBetween(dateStart, dateEnd).stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteUser(Long userID) {
        var presentCheck = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found!"));
        userRepository.delete(presentCheck);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User createResponse = modelMapper.map(userDTO, User.class);
        userRepository.save(createResponse);
        return modelMapper.map(createResponse, UserDTO.class);
    }

    public UserDTO updateUser(UserDTO updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getUserID()).orElseThrow(() -> new RuntimeException("User not found!"));
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setBirthdate(updatedUser.getBirthdate());
        User updateResponse = userRepository.save(existingUser);
        return modelMapper.map(updateResponse, UserDTO.class);

    }
}
