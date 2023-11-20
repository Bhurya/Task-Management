package com.taskmanagement.dto;

import lombok.Data;

@Data
public class UserDto {

    private String name;

    private String email;

    private String mobileNumber;

    private String address;

    private String designation;

    private Long roleId;

    private String password;

}
