package ru.vsu.museum.service;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.db.DBCategoryRepository;
import ru.vsu.museum.persistence.repositories.db.DBExponentRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    private Repository<Category> categoryRepository = new DBCategoryRepository();
    private Repository<Exponent> exponentRepository = new DBExponentRepository();

    public List<Category> getAll() {
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

    public List<Exponent> getExponents(long id) {
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
            if (category.getCategoryId() > id)
                id = category.getCategoryId();
        }
        return id;
    }

    public void update(Category item) {
        categoryRepository.update(item);
    }
}
