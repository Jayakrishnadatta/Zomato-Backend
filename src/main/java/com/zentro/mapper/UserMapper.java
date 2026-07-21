package com.zentro.mapper;

import com.zentro.dto.user.UserRegisterDto;
import com.zentro.dto.user.UserResponseDto;
import com.zentro.model.User;

public class UserMapper {

    private UserMapper() {
    }

    // Register DTO → Entity

    public static User toEntity(
            UserRegisterDto dto
    ) {

        User user = new User();

        user.setFirstName(
                dto.getFirstName()
        );

        user.setLastName(
                dto.getLastName()
        );

        user.setEmail(
                dto.getEmail()
        );

        user.setMobileNumber(
                dto.getMobileNumber()
        );

        user.setPasswordHash(
                dto.getPassword()
        );

        return user;
    }

    // Entity → Response DTO

    public static UserResponseDto
    toResponse(User user) {

        UserResponseDto response =
                new UserResponseDto();

        response.setUserId(
                user.getId()
        );

        response.setFirstName(
                user.getFirstName()
        );

        response.setLastName(
                user.getLastName()
        );

        response.setEmail(
                user.getEmail()
        );

        response.setMobileNumber(
                user.getMobileNumber()
        );

        response.setRole(
                user.getRole()
        );

        return response;
    }
    
    

    
}