package com.lab.ecommercebackend.dto;


public record CreateUserDto(

	String name,
	String lastName,
    String email,
    String password,
    String role
        ){}
