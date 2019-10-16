package ru.vsu.museum.service;

import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.persistence.repositories.ExponentRepository;

import java.util.ArrayList;

public class ExponentService {
    private ExponentRepository exponentRepository = ExponentRepository.getInstance();

    public ArrayList<Exponent> getAll() {
        return exponentRepository.getAll();
    }

    public Exponent getById(long id) {
        return exponentRepository.getById(id);
    }

    public void deleteById(long id) {
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
