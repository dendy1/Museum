package ru.vsu.museum.persistence.repositories;

import ru.vsu.museum.domain.Author;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;

public class AuthorRepository implements Repository<Author> {
    private ArrayList<Author> authors;

    public AuthorRepository() {
        authors = new ArrayList<Author>();
        for (long i = 1; i < 5; i++) {
            authors.add(new Author(i, "Author name" + i));
        }
    }

    public ArrayList<Author> getAll() {
        return authors;
    }

    public Author getById(Long id) {
        for (Author author : authors) {
            if (author.getId().equals(id))
                return author;
        }

        return null;
    }

    public void create(Author item) {
        authors.add(item);
    }

    public void delete(Long id) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getId().equals(id)) {
                authors.remove(i);
                return;
            }
        }
    }

    public void update(Author item) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getId().equals(item.getId())) {
                authors.set(i, item);
                return;
            }
        }
    }
}
