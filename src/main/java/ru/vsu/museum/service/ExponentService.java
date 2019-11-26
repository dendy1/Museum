package ru.vsu.museum.service;

import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.ExponentOnExhibition;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.db.DBExponentOnExhibitionRepository;
import ru.vsu.museum.persistence.repositories.db.DBExponentRepository;
import ru.vsu.museum.persistence.repositories.inMemory.ExponentOnExhibitionRepository;

import java.util.List;

public class ExponentService {
    private Repository<Exponent> exponentRepository = new DBExponentRepository();
    private Repository<ExponentOnExhibition> exponentOnExhibitionRepository = new DBExponentOnExhibitionRepository();

    public List<Exponent> getAll() {
        return exponentRepository.getAll();
    }

    public Exponent getById(long id) {
        return exponentRepository.getById(id);
    }

    public void deleteById(long id) {
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
            if (exponent.getExponentId() > id)
                id = exponent.getExponentId();
        }
        return id;
    }

    public void update(Exponent item) {
        exponentRepository.update(item);
    }
}
