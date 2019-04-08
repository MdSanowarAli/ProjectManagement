package com.infoistic.dao;

import java.util.List;

public interface IRepository<T>{
	T getById(int Id) throws Exception;
	List<T> get() throws Exception;
    int Add(T entity) throws Exception;
    void Update(T entity) throws Exception;
    void remove(int id) throws Exception;
}

