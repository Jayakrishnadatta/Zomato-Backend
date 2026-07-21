package com.zentro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zentro.dto.user.UserResponseDto;
import com.zentro.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
	@Query("select new com.zentro.dto.user.UserResponseDto(u.Id, u.firstName, u.lastName, u.email, u.mobileNumber, u.role) from User u where u.email = :email")
	UserResponseDto findUserResponseByEmail(@Param("email") String email);


	@Query("select new com.zentro.dto.user.UserResponseDto(u.Id, u.firstName, u.lastName, u.email, u.mobileNumber, u.role) from User u where u.Id = :userId")
	UserResponseDto findUserResponseById(@Param("userId") Long userId);

	
	@Query("select new com.zentro.dto.user.UserResponseDto(u.Id, u.firstName, u.lastName, u.email, u.mobileNumber, u.role) from User u")
	List<UserResponseDto> findAllUser();
	
	
	
}
