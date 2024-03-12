package com.lab.ecommercebackend.dto;

import com.lab.ecommercebackend.model.Role;

import java.util.List;

public record RecoveryUserDto(

        Long id,
        String email,
        List<Role> roles
) {
}