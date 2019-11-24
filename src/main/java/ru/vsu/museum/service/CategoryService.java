package ru.vsu.museum.service;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.persistence.repositories.inMemory.CategoryRepository;
import ru.vsu.museum.persistence.repositories.inMemory.ExponentRepository;

import java.util.ArrayList;

public class CategoryService {
    private CategoryRepository categoryRepository = CategoryRepository.getInstance();
    private ExponentRepository exponentRepository = ExponentRepository.getInstance();

    public ArrayList<Category> getAll() {
        return categoryRepository.getAll();
    }

    public Category getById(long id) {
        return categoryRepository.getById(id);
    }

    public void deleteById(long id) {
        for (Exponent exponent : exponentRepository.getAll()) {
            if (exponent.getCategoryId().equals(id)) {
                exponent.setCategoryId(null);
                exponentRepository.update(exponent);
            }
        }
        categoryRepository.delete(id);
    }

    public void add(Category item) {
        categoryRepository.create(item);
    }

    public ArrayList<Exponent> getExponents(long id) {
        ArrayList<Exponent> exponents = new ArrayList<Exponent>();
        for (Exponent exponent: exponentRepository.getAll()) {
            if (exponent.getCategoryId().equals(id)) {
                exponents.add(exponent);
            }
        }
        return exponents;
    }

    public long getLastId()
    {
        long id = 0;
        for (Category category: categoryRepository.getAll()) {
            if (category.getId() > id)
                id = category.getId();
        }
        return id;
    }
}
