package ru.vsu.museum.persistence;

import ru.vsu.museum.domain.Category;

import java.util.List;

public interface CategoryStore {
    List<Category> getCategories();
    Category getCategoryById(Long id);
    Long getCount();
}
