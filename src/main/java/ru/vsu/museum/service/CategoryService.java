package ru.vsu.museum.service;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.db.DBRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryService {

    private Repository<Category> categoryRepository =
            new DBRepository<Category>(Category.class, "category", "categoryId");
    private Repository<Exponent> exponentRepository =
            new DBRepository<Exponent>(Exponent.class, "exponent", "exponentId");

    public List<Category> getAll() {
        return categoryRepository.getAll(null);
    }

    public Category getById(long categoryId) {
        return categoryRepository.getById(categoryId);
    }

    public void deleteById(long categoryId) {
        for (Exponent exponent : exponentRepository.getAll("categoryId="+categoryId)) {
            exponent.setCategoryId(null);
            exponentRepository.update(exponent);
        }
        categoryRepository.delete(categoryId);
    }

    public void add(Category item) {
        categoryRepository.create(item);
    }

    public List<Exponent> getExponents(long categoryId) {
        return exponentRepository.getAll("categoryId="+categoryId);
    }

    public void update(Category item) {
        categoryRepository.update(item);
    }
}
