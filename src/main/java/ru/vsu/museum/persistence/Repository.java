package ru.vsu.museum.persistence;

import java.util.List;
import java.util.Map;

public interface Repository<T> {
    List<T> getAll(String where);
    T getById(Long id);
    boolean create(T item);
    boolean update(T item);
    boolean delete(Long id);
    Long getCount();
}
