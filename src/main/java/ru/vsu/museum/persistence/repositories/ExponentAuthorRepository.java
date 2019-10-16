package ru.vsu.museum.persistence.repositories;

import ru.vsu.museum.domain.ExponentAuthor;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;

public class ExponentAuthorRepository implements Repository<ExponentAuthor> {
    private static ExponentAuthorRepository instance;
    public static ExponentAuthorRepository getInstance() {
        if (instance == null) {
            instance = new ExponentAuthorRepository();
        }
        return instance;
    }
    private ArrayList<ExponentAuthor> exponentAuthors = new ArrayList<ExponentAuthor>();

    public ArrayList<ExponentAuthor> getAll() {
        return exponentAuthors;
    }

    public ExponentAuthor getById(Long id) {
        for (ExponentAuthor exponentAuthor: exponentAuthors) {
            if (exponentAuthor.getId().equals(id)) {
                return exponentAuthor;
            }
        }
        return null;
    }

    public void create(ExponentAuthor item) {
        exponentAuthors.add(item);
    }

    public void delete(Long id) {
        for (int i = 0; i < exponentAuthors.size(); i++) {
            if (exponentAuthors.get(i).getId().equals(id)) {
                exponentAuthors.remove(i);
                return;
            }
        }
    }

    public void update(ExponentAuthor item) {
        for (int i = 0; i < exponentAuthors.size(); i++) {
            if (exponentAuthors.get(i).getId().equals(item.getId())) {
                exponentAuthors.remove(i);
                return;
            }
        }
    }
}
