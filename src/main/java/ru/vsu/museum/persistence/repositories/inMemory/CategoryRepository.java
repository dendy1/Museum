package ru.vsu.museum.persistence.repositories.inMemory;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements Repository<Category> {
    private Long lastId;
    private static CategoryRepository instance;
    public static CategoryRepository getInstance() {
        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }
    private ArrayList<Category> categories;

    public CategoryRepository() {
        categories = new ArrayList<Category>();
        for (long i = 0; i < 2; i++)
        {
            categories.add(new Category(i, "Category " + i, "Description for category " + i));
        }
        lastId = getCount();
    }

    @Override
    public List<Category> getAll(String where) {
        if (where == null)
        {
            return categories;
        }

        return null;
    }

    public Category getById(Long id) {
        for (Category category : categories) {
            if (category.getCategoryId().equals(id))
                return category;
        }
        return null;
    }

    public boolean create(Category item) {
        item.setCategoryId(++lastId);
        categories.add(item);
        return true;
    }

    public boolean delete(Long id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId().equals(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean update(Category item) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId().equals(item.getCategoryId())) {
                categories.set(i, item);
                return true;
            }
        }
        return false;
    }

    public Long getCount() {
        return (long)categories.size();
    }
}
