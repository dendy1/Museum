package ru.vsu.museum.service;

import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.ExponentAuthor;
import ru.vsu.museum.domain.ExponentOnExhibition;
import ru.vsu.museum.persistence.repositories.ExponentAuthorRepository;
import ru.vsu.museum.persistence.repositories.ExponentOnExhibitionRepository;
import ru.vsu.museum.persistence.repositories.ExponentRepository;

import java.util.ArrayList;

public class ExponentService {
    private ExponentRepository exponentRepository = ExponentRepository.getInstance();
    private ExponentAuthorRepository exponentAuthorRepository = ExponentAuthorRepository.getInstance();
    private ExponentOnExhibitionRepository exponentOnExhibitionRepository = ExponentOnExhibitionRepository.getInstance();

    public ArrayList<Exponent> getAll() {
        return exponentRepository.getAll();
    }

    public Exponent getById(long id) {
        return exponentRepository.getById(id);
    }

    public void deleteById(long id) {
        for (ExponentAuthor exponentAuthor : exponentAuthorRepository.getAll()) {
            if (exponentAuthor.getExponentId().equals(id))
            {
                exponentAuthorRepository.delete(exponentAuthor.getId());
                break;
            }
        }

        for (ExponentOnExhibition exponentOnExhibition : exponentOnExhibitionRepository.getAll()) {
            if (exponentOnExhibition.getExponentId().equals(id))
            {
                exponentOnExhibitionRepository.delete(exponentOnExhibition.getId());
                break;
            }
        }

        exponentRepository.delete(id);
    }

    public void add(Exponent item) {
        exponentRepository.create(item);
    }

    public long getLastId()
    {
        long id = 0;
        for (Exponent exponent: exponentRepository.getAll()) {
            if (exponent.getId() > id)
                id = exponent.getId();
        }
        return id;
    }
}
