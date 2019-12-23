package ru.vsu.museum.service;

import ru.vsu.museum.domain.Exhibition;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.ExponentOnExhibition;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.TableUtils;
import ru.vsu.museum.persistence.repositories.db.DBRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExhibitionService {
    private Repository<Exponent> exponentRepository =
            new DBRepository<Exponent>(Exponent.class, "exponent", "exponentId");
    private Repository<Exhibition> exhibitionRepository =
            new DBRepository<Exhibition>(Exhibition.class, "exhibition", "exhibitionId");
    private Repository<ExponentOnExhibition> exponentOnExhibitionRepository =
            new DBRepository<ExponentOnExhibition>(ExponentOnExhibition.class,
                    "exponent_on_exhibition", "id");

    public List<Exhibition> getAll() {
        return exhibitionRepository.getAll(null);
    }

    public Exhibition getById(long id) {
        return exhibitionRepository.getById(id);
    }

    public void deleteById(long exhibitionId) {
        for (ExponentOnExhibition exponentOnExhibition :
                exponentOnExhibitionRepository.getAll("exhibitionId="+exhibitionId)) {
            exponentOnExhibitionRepository.delete(exponentOnExhibition.getId());
        }
        exhibitionRepository.delete(exhibitionId);
    }

    public void add(Exhibition item) {
        exhibitionRepository.create(item);
    }

    public void addWithExponents(Exhibition exhibition, List<Long> exponentsIds) {
        exhibitionRepository.create(exhibition);
        long exhibitionId = TableUtils.getLastInsertedId();
        for (Long exponentId: exponentsIds)
        {
            ExponentOnExhibition ee = new ExponentOnExhibition(null, exhibitionId, exponentId);
            exponentOnExhibitionRepository.create(ee);
        }
    }

    public void addExponent(long exhibitionId, long exponentId)
    {
        exponentOnExhibitionRepository.create(
                new ExponentOnExhibition(
                        exponentOnExhibitionRepository.getCount() + 1, exhibitionId, exponentId));
    }

    public List<Exponent> getExponents(Long exhibitionId) {
        //return exponentRepository.getAll("exhibitionId="+exhibitionId);
        List<Exponent> exponents = new ArrayList<Exponent>();
        for (ExponentOnExhibition exponentOnExhibition:
                exponentOnExhibitionRepository.getAll("exhibitionId="+exhibitionId)) {
            exponents.add(exponentRepository.getById(exponentOnExhibition.getExponentId()));
        }
        return exponents;
    }

    public void update(Exhibition item) {
        exhibitionRepository.update(item);
    }

    public void updateWithExponents(Exhibition exhibition, List<Long> exponentsIds) {
        exhibitionRepository.update(exhibition);

        for (ExponentOnExhibition exponentOnExhibition:
                exponentOnExhibitionRepository.getAll("exhibitionId="+exhibition.getExhibitionId())) {
            exponentOnExhibitionRepository.delete(exponentOnExhibition.getId());
        }

        for (Long exponentId: exponentsIds) {
            ExponentOnExhibition ee = new ExponentOnExhibition(null, exhibition.getExhibitionId(), exponentId);
            exponentOnExhibitionRepository.create(ee);
        }
    }

    public Long getCount()
    {
        return exhibitionRepository.getCount();
    }
}
