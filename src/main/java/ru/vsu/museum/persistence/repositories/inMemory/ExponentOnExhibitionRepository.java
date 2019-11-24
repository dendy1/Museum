package ru.vsu.museum.persistence.repositories.inMemory;

import ru.vsu.museum.domain.ExponentOnExhibition;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;

public class ExponentOnExhibitionRepository implements Repository<ExponentOnExhibition> {
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
            exponentOnExhibitions.add(new ExponentOnExhibition((long)(exponentOnExhibitions.size()), i, i % 2));
        }
    }

    public ArrayList<ExponentOnExhibition> getAll() {
        return exponentOnExhibitions;
    }

    public ExponentOnExhibition getById(Long id) {
        for (ExponentOnExhibition exponentOnExhibition: exponentOnExhibitions) {
            if (exponentOnExhibition.getId().equals(id)) {
                return exponentOnExhibition;
            }
        }
        return null;
    }

    public void create(ExponentOnExhibition item) {
        exponentOnExhibitions.add(item);
    }

    public void delete(Long id) {
        for (int i = 0; i < exponentOnExhibitions.size(); i++) {
            if (exponentOnExhibitions.get(i).getId().equals(id)) {
                exponentOnExhibitions.remove(i);
                return;
            }
        }
    }

    public void update(ExponentOnExhibition item) {
        for (int i = 0; i < exponentOnExhibitions.size(); i++) {
            if (exponentOnExhibitions.get(i).getId().equals(item.getId())) {
                exponentOnExhibitions.set(i, item);
                return;
            }
        }
    }
}