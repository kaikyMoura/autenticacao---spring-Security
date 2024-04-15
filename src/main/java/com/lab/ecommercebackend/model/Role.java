package com.lab.ecommercebackend.model;

import com.lab.ecommercebackend.enums.RoleName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Role {

    @Enumerated(EnumType.STRING)
    private RoleName name;
}