package com.andrescarvajald.unitrack.api;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public interface IAPI<T> {
    public List<T> get(String endpoint, TypeReference<List<T>> t);
    public T get(String endpoint, Class<T> t);
}
