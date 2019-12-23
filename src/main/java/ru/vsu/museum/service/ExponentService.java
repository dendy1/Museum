package ru.vsu.museum.service;

import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.ExponentOnExhibition;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.db.DBRepository;

import java.util.List;

public class ExponentService {
    private Repository<Exponent> exponentRepository =
            new DBRepository<Exponent>(Exponent.class, "exponent", "exponentId");
    private Repository<ExponentOnExhibition> exponentOnExhibitionRepository =
            new DBRepository<ExponentOnExhibition>(ExponentOnExhibition.class,
                    "exponent_on_exhibition", "id");

    public List<Exponent> getAll() {
        return exponentRepository.getAll(null);
    }

    public Exponent getById(long id) {
        return exponentRepository.getById(id);
    }

    public void deleteById(long exponentId) {
        for (ExponentOnExhibition exponentOnExhibition :
                exponentOnExhibitionRepository.getAll("exponentId="+exponentId)) {
            exponentOnExhibitionRepository.delete(exponentOnExhibition.getId());
        }

        exponentRepository.delete(exponentId);
    }

    public void add(Exponent item) {
        exponentRepository.create(item);
    }

    public void update(Exponent item) {
        exponentRepository.update(item);
    }

    public Long getCount() { return exponentRepository.getCount(); }
}
