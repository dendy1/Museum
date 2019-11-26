package ru.vsu.museum.persistence;

import java.util.List;

public interface Repository<T> {
    List<T> getAll();
    T getById(Long id);
    boolean create(T item);
    boolean update(T item);
    boolean delete(Long id);
    Long getCount();
}
