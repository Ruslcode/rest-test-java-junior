package com.clear.solutions.source.configuration;

import com.clear.solutions.source.entities.User;
import com.clear.solutions.source.entities.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig extends ModelMapper {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configureUserMapping(modelMapper);
        return modelMapper;
    }

    private void configureUserMapping(ModelMapper modelMapper) {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMapping(User::getUserID, UserDTO::setUserID)
                .addMapping(User::getFirstName, UserDTO::setFirstName)
                .addMapping(User::getLastName, UserDTO::setLastName)
                .addMapping(User::getEmail, UserDTO::setEmail)
                .addMapping(User::getBirthdate, UserDTO::setBirthdate)
                .addMapping(User::getAddress, UserDTO::setAddress)
                .addMapping(User::getPhoneNumber, UserDTO::setPhoneNumber);
    }
}
