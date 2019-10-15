package ru.vsu.museum.persistence;

import java.util.ArrayList;

public interface Repository<T> {
    ArrayList<T> getAll();
    T getById(Long id);
    void create(T item);
    void delete(Long id);
    void update(T item);
}
