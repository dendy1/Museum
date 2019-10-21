package ru.vsu.museum.service;

import ru.vsu.museum.domain.Author;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.ExponentAuthor;
import ru.vsu.museum.persistence.repositories.AuthorRepository;
import ru.vsu.museum.persistence.repositories.ExponentAuthorRepository;
import ru.vsu.museum.persistence.repositories.ExponentRepository;

import java.util.ArrayList;

public class AuthorService {
    private AuthorRepository authorRepository = AuthorRepository.getInstance();
    private ExponentRepository exponentRepository = ExponentRepository.getInstance();
    private ExponentAuthorRepository exponentAuthorRepository = ExponentAuthorRepository.getInstance();

    public ArrayList<Author> getAll() {
        return authorRepository.getAll();
    }

    public Author getById(Long id) { return authorRepository.getById(id); }

    public void deleteById(Long id) {
        for (ExponentAuthor exponentAuthor : exponentAuthorRepository.getAll()) {
            if (exponentAuthor.getAuthorId().equals(id))
            {
                exponentAuthorRepository.delete(exponentAuthor.getId());
                break;
            }
        }

        authorRepository.delete(id);
    }

    public void add(Author item)
    {
        authorRepository.create(item);
    }

    public long getLastId()
    {
        long id = 0;
        for (Author author: authorRepository.getAll()) {
            if (author.getId() > id)
                id = author.getId();
        }
        return id;
    }

    public ArrayList<Exponent> getExponents(Long id) {
        ArrayList<Exponent> exponents = new ArrayList<Exponent>();
        for (ExponentAuthor exponentAuthor: exponentAuthorRepository.getAll()) {
            if (exponentAuthor.getAuthorId().equals(id)) {
                exponents.add(exponentRepository.getById(exponentAuthor.getExponentId()));
            }
        }
        return exponents;
    }
}
