package com.lab.ecommercebackend.repository;

import com.lab.ecommercebackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends MongoRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
