package com.taskmanagement.service.impl;

import com.taskmanagement.config.JwtHelper;
import com.taskmanagement.dto.LoginRequest;
import com.taskmanagement.dto.LoginResponse;
import com.taskmanagement.dto.UserDto;
import com.taskmanagement.exception.GenericExceptions;
import com.taskmanagement.model.Role;
import com.taskmanagement.model.User;
import com.taskmanagement.repository.RoleRepository;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public User createUser(UserDto user) {

        Role role = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new GenericExceptions("Invalid Role Found !!"));

        User userObject = User.builder()
                .name(user.getName())
                .role(role)
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .address(user.getAddress())
                .designation(user.getDesignation())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .build();
        userRepository.save(userObject);
        return userObject;
    }

    @Override
    public User updateUser(UserDto user) {
        return null;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findByUserIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new GenericExceptions("User not found"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findByIsDeletedFalse();
    }

    @Override
    public String deleteUserById(Long id) {
        return userRepository.findByUserIdAndIsDeletedFalse(id)
                .map(user -> {
                    userRepository.delete(user);
                    return "User deleted successfully";
                })
                .orElse("User not found");
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByUserName(loginRequest.getEmail()).orElseThrow(() -> new GenericExceptions("User Not Found !!"));

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        }catch (Exception e){
            e.printStackTrace();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());


        String token = jwtHelper.generateToken(userDetails);
        return new LoginResponse(user.getUserId(), token, user.getEmail());


    }


}
