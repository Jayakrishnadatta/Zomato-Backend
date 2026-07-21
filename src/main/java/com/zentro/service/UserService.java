package com.zentro.service;

import java.util.List;

import com.zentro.dto.user.LoginDto;
import com.zentro.dto.user.UserRegisterDto;
import com.zentro.dto.user.UserResponseDto;

public interface UserService {

    UserResponseDto registerUser(
            UserRegisterDto dto
    );

    UserResponseDto loginUser(
            LoginDto dto
    );

    UserResponseDto getUserById(
            Long userId
    );

     List<UserResponseDto> getAllUser();

    UserResponseDto updateUser(
            Long userId,
            UserRegisterDto dto
    );

    void deleteUser(Long userId);

    void changePassword(
            Long userId,
            UserRegisterDto dto
    );
}