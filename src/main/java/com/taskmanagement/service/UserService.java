package com.taskmanagement.service;

import com.taskmanagement.dto.LoginRequest;
import com.taskmanagement.dto.LoginResponse;
import com.taskmanagement.dto.TaskDto;
import com.taskmanagement.dto.UserDto;
import com.taskmanagement.model.User;

import java.util.List;

public interface UserService {

    User createUser(UserDto user);

    User updateUser(UserDto user);


    User findUserById(Long id);

    List<User> findAllUsers();

    String deleteUserById(Long id);

    LoginResponse login(LoginRequest loginRequest);
}
