package com.andrescarvajald.unitrack.repositories;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public interface IAPI<T> {
    public List<T> get(String endpoint, TypeReference<List<T>> t);
    public T get(String endpoint, Class<T> t);
    public void update(String endpoint, T t);
    public void add(String endpoint, T t);
}
