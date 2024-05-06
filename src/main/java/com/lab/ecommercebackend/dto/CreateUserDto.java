package com.lab.ecommercebackend.dto;


public record CreateUserDto(

    String email,
    String password,
    String role
        ){}
