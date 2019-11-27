package ru.vsu.museum.persistence.repositories.inMemory;

import ru.vsu.museum.domain.ExponentOnExhibition;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExponentOnExhibitionRepository implements Repository<ExponentOnExhibition> {
    private Long lastId;

    private static ExponentOnExhibitionRepository instance;
    public static ExponentOnExhibitionRepository getInstance() {
        if (instance == null) {
            instance = new ExponentOnExhibitionRepository();
        }
        return instance;
    }
    private ArrayList<ExponentOnExhibition> exponentOnExhibitions = new ArrayList<ExponentOnExhibition>();

    public ExponentOnExhibitionRepository() {
        for (long i = 0; i < 10; i++) {
            exponentOnExhibitions.add(new ExponentOnExhibition((long)(exponentOnExhibitions.size()), i % 2, i));
        }
        lastId = getCount();
    }

    @Override
    public List<ExponentOnExhibition> getAll(String where) {
        return null;
    }

    public ExponentOnExhibition getById(Long id) {
        for (ExponentOnExhibition exponentOnExhibition: exponentOnExhibitions) {
            if (exponentOnExhibition.getId().equals(id)) {
                return exponentOnExhibition;
            }
        }
        return null;
    }

    public boolean create(ExponentOnExhibition item) {
        item.setId(++lastId);
        exponentOnExhibitions.add(item);
        return true;
    }

    public boolean delete(Long id) {
        for (int i = 0; i < exponentOnExhibitions.size(); i++) {
            if (exponentOnExhibitions.get(i).getId().equals(id)) {
                exponentOnExhibitions.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean update(ExponentOnExhibition item) {
        for (int i = 0; i < exponentOnExhibitions.size(); i++) {
            if (exponentOnExhibitions.get(i).getId().equals(item.getId())) {
                exponentOnExhibitions.set(i, item);
                return true;
            }
        }
        return false;
    }

    public Long getCount() {
        return (long)exponentOnExhibitions.size();
    }
}
