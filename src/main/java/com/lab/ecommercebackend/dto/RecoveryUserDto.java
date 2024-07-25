package com.lab.ecommercebackend.dto;


public record RecoveryUserDto (
	String name,
	String lastName,
	String image,
    String email
) {}