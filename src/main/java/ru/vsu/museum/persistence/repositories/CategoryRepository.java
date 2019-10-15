package ru.vsu.museum.persistence.repositories;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;

public class CategoryRepository implements Repository<Category> {
    private ArrayList<Category> categories;

    public CategoryRepository() {
        categories = new ArrayList<Category>();
        for (long i = 1; i < 3; i++)
        {
            categories.add(new Category(i, "Category" + i, "Description for category " + i));
        }
    }

    public ArrayList<Category> getAll() {
        return categories;
    }

    public Category getById(Long id) {
        for (Category category : categories) {
            if (category.getId().equals(id))
                return category;
        }
        return null;
    }

    public void create(Category item) {
        categories.add(item);
    }

    public void delete(Long id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.remove(i);
                return;
            }
        }
    }

    public void update(Category item) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(item.getId())) {
                categories.set(i, item);
                return;
            }
        }
    }
}
