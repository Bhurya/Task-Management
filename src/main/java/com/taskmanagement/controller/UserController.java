package com.taskmanagement.controller;

import com.taskmanagement.dto.LoginRequest;
import com.taskmanagement.dto.LoginResponse;
import com.taskmanagement.dto.UserDto;
import com.taskmanagement.model.User;
import com.taskmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("createNewUser")
    public ResponseEntity<User> createNewUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @Operation(summary = "getUserById endpoint", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("getUserById")
    public ResponseEntity<User> getUserById(Long userId) {
        User user = userService.findUserById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @Operation(summary = "deleteUserById endpoint", security = @SecurityRequirement(name = "Authorization"))
    @DeleteMapping("deleteUserById")
    public ResponseEntity<String> deleteUserById(Long userId) {
        String user = userService.deleteUserById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @Operation(summary = "findListOfUsers endpoint", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("findListOfUsers")
    public ResponseEntity<List<User>> findListOfUsers() {
        List<User> user = userService.findAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);

    }

}
