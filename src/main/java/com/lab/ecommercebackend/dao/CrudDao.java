package com.lab.ecommercebackend.dao;

import java.io.Serializable;

public interface CrudDao<T, ID extends Serializable> {

    void create(T t);

    void deleteById(ID id) throws Exception;

    void update(ID id, T t);

    void getByID(ID id) throws Exception;
}
