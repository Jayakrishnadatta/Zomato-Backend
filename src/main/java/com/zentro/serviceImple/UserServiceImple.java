package com.zentro.serviceImple;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zentro.dto.user.LoginDto;
import com.zentro.dto.user.UserRegisterDto;
import com.zentro.dto.user.UserResponseDto;
import com.zentro.mapper.UserMapper;
import com.zentro.model.User;
import com.zentro.repository.UserRepository;
import com.zentro.service.UserService;

@Service
public class UserServiceImple implements UserService {

    private final UserRepository userRepository;

    public UserServiceImple(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto registerUser(UserRegisterDto dto) {
        validateRegisterDto(dto);

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = UserMapper.toEntity(dto);
        user.setRole("USER");
        user.setActive(true);
        user.setAccountVerified(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDto loginUser(LoginDto dto) {
        validateLoginDto(dto);

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!dto.getPassword().equals(user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (!user.isActive()) {
            throw new IllegalArgumentException("User account is inactive");
        }

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        validateUserId(userId);

        UserResponseDto response = userRepository.findUserResponseById(userId);
        if (response == null) {
            throw new IllegalArgumentException("User not found");
        }
        return response;
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRegisterDto dto) {
        validateUserId(userId);
        validateRegisterDto(dto);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.findByEmail(dto.getEmail())
                .filter(existingUser -> !userId.equals(existingUser.getId()))
                .ifPresent(existingUser -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setMobileNumber(dto.getMobileNumber());
        user.setPasswordHash(dto.getPassword());
        user.setUpdatedAt(LocalDateTime.now());

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        validateUserId(userId);
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }

        userRepository.deleteById(userId);
    }

    @Override
    public void changePassword(Long userId, UserRegisterDto dto) {
        validateUserId(userId);
        if (dto == null || dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPasswordHash(dto.getPassword());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        return userRepository.findAllUser();
    }

    private void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User is required");
        }
    }

    private void validateLoginDto(LoginDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Login data is required");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
    }

    private void validateRegisterDto(UserRegisterDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("User data is required");
        }
        if (dto.getFirstName() == null || dto.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
    }
}
