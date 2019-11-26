package ru.vsu.museum.persistence;

import java.util.List;

public interface Repository<T> {
    List<T> getAll();
    T getById(Long id);
    Boolean create(T item);
    Boolean update(T item);
    Boolean delete(Long id);
    Long getCount();
}
