package ru.vsu.museum.service;

import ru.vsu.museum.domain.Exhibition;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.ExponentOnExhibition;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.db.DBExhibitionRepository;
import ru.vsu.museum.persistence.repositories.db.DBExponentRepository;
import ru.vsu.museum.persistence.repositories.inMemory.ExponentOnExhibitionRepository;

import java.util.ArrayList;
import java.util.List;

public class ExhibitionService {
    private Repository<Exponent> exponentRepository = new DBExponentRepository();
    private Repository<Exhibition> exhibitionRepository = new DBExhibitionRepository();

    private Repository<ExponentOnExhibition> exponentOnExhibitionRepository = ExponentOnExhibitionRepository.getInstance();

    public List<Exhibition> getAll() {
        return exhibitionRepository.getAll();
    }

    public Exhibition getById(long id) {
        return exhibitionRepository.getById(id);
    }

    public void deleteById(long id) {
        for (ExponentOnExhibition exponentOnExhibition : exponentOnExhibitionRepository.getAll()) {
            if (exponentOnExhibition.getExhibitionId().equals(id))
            {
                exponentOnExhibitionRepository.delete(exponentOnExhibition.getId());
                break;
            }
        }
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

        exponentOnExhibitionRepository.create(new ExponentOnExhibition(id, exponentId, exhibitionId));
    }

    public long getLastId()
    {
        long id = 0;
        for (Exhibition exhibition: exhibitionRepository.getAll()) {
            if (exhibition.getExhibitionId() > id)
                id = exhibition.getExhibitionId();
        }
        return id;
    }

    public List<Exponent> getExponents(Long id) {
        List<Exponent> exponents = new ArrayList<Exponent>();
        for (ExponentOnExhibition exponentAuthor: exponentOnExhibitionRepository.getAll()) {
            if (exponentAuthor.getExhibitionId().equals(id)) {
                exponents.add(exponentRepository.getById(exponentAuthor.getExponentId()));
            }
        }
        return exponents;
    }

    public void update(Exhibition item) {
        exhibitionRepository.update(item);
    }
}
