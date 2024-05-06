package com.lab.ecommercebackend.repository;

import com.lab.ecommercebackend.enums.RoleName;
import com.lab.ecommercebackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
