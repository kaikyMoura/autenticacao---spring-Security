package com.lab.ecommercebackend.dao;

import java.io.Serializable;
import java.util.UUID;

import com.lab.ecommercebackend.dto.LoginUserDto;
import com.lab.ecommercebackend.model.User;

public interface CrudDao<T, ID extends Serializable> {

    void create(T t);

    void update(ID id, T t);

    Object getByID(ID id) throws Exception;

	void deleteById(ID id) throws Exception;
}
