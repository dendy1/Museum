package ru.vsu.museum.persistence.repositories.inMemory;

import ru.vsu.museum.domain.Exhibition;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;
import java.util.Date;

public class ExhibitionRepository implements Repository<Exhibition> {
    private static ExhibitionRepository instance;
    public static ExhibitionRepository getInstance() {
        if (instance == null) {
            instance = new ExhibitionRepository();
        }
        return instance;
    }
    private ArrayList<Exhibition> exhibitions;

    public ExhibitionRepository() {
        exhibitions = new ArrayList<Exhibition>();
        for (long i = 0; i < 2; i++)
        {
            exhibitions.add(new Exhibition(i, new Date(), new Date(), "Exhibiton " + i, "Russia", "Valeriy Albertovich"));
        }
    }

    public ArrayList<Exhibition> getAll() {
        return exhibitions;
    }

    public Exhibition getById(Long id) {
        for (Exhibition exhibition : exhibitions) {
            if (exhibition.getId().equals(id))
                return exhibition;
        }
        return null;
    }

    public void create(Exhibition item) {
        exhibitions.add(item);
    }

    public void delete(Long id) {
        for (int i = 0; i < exhibitions.size(); i++) {
            if (exhibitions.get(i).getId().equals(id)) {
                exhibitions.remove(i);
                return;
            }
        }
    }

    public void update(Exhibition item) {
        for (int i = 0; i < exhibitions.size(); i++) {
            if (exhibitions.get(i).getId().equals(item.getId())) {
                exhibitions.set(i, item);
                return;
            }
        }
    }
}
