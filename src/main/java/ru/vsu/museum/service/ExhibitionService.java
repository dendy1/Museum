package ru.vsu.museum.service;

import ru.vsu.museum.domain.Exhibition;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.ExponentOnExhibition;
import ru.vsu.museum.persistence.repositories.ExhibitionRepository;
import ru.vsu.museum.persistence.repositories.ExponentOnExhibitionRepository;
import ru.vsu.museum.persistence.repositories.ExponentRepository;

import java.util.ArrayList;

public class ExhibitionService {
    private ExponentRepository exponentRepository = ExponentRepository.getInstance();
    private ExhibitionRepository exhibitionRepository = ExhibitionRepository.getInstance();
    private ExponentOnExhibitionRepository exponentOnExhibitionRepository = ExponentOnExhibitionRepository.getInstance();

    public ArrayList<Exhibition> getAll() {
        return exhibitionRepository.getAll();
    }

    public Exhibition getById(long id) {
        return exhibitionRepository.getById(id);
    }

    public void deleteById(long id) {
        exhibitionRepository.delete(id);
    }

    public void add(Exhibition item) {
        exhibitionRepository.create(item);
    }

    public void addExponent(long exhibitionId, long exponentId)
    {
        long id = 0;
        for (ExponentOnExhibition exponentOnExhibition: exponentOnExhibitionRepository.getAll()) {
            if (exponentOnExhibition.getId() > id)
                id = exponentOnExhibition.getId();
        }

        exponentOnExhibitionRepository.create(new ExponentOnExhibition(id, exhibitionId, exponentId));
    }

    public long getLastId()
    {
        long id = 0;
        for (Exhibition exhibition: exhibitionRepository.getAll()) {
            if (exhibition.getId() > id)
                id = exhibition.getId();
        }
        return id;
    }

    public ArrayList<Exponent> getExponents(Long id) {
        ArrayList<Exponent> exponents = new ArrayList<Exponent>();
        for (ExponentOnExhibition exponentAuthor: exponentOnExhibitionRepository.getAll()) {
            if (exponentAuthor.getExhibitionId().equals(id)) {
                exponents.add(exponentRepository.getById(exponentAuthor.getExponentId()));
            }
        }
        return exponents;
    }
}
