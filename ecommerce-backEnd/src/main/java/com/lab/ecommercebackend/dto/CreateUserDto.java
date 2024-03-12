package com.lab.ecommercebackend.dto;

import com.lab.ecommercebackend.enums.RoleName;

public record CreateUserDto(

    String email,
    String password,
    RoleName role
        ){}
