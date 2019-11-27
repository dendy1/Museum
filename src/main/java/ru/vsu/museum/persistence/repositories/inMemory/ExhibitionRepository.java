package ru.vsu.museum.persistence.repositories.inMemory;

import ru.vsu.museum.domain.Exhibition;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExhibitionRepository implements Repository<Exhibition> {
    private Long lastId;
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
            exhibitions.add(new Exhibition(i, new Date(), "Exhibiton " + i));
        }
        lastId = getCount();
    }

    @Override
    public List<Exhibition> getAll(String where) {
        return null;
    }

    public Exhibition getById(Long id) {
        for (Exhibition exhibition : exhibitions) {
            if (exhibition.getExhibitionId().equals(id))
                return exhibition;
        }
        return null;
    }

    public boolean create(Exhibition item) {
        item.setExhibitionId(++lastId);
        exhibitions.add(item);
        return true;
    }

    public boolean delete(Long id) {
        for (int i = 0; i < exhibitions.size(); i++) {
            if (exhibitions.get(i).getExhibitionId().equals(id)) {
                exhibitions.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean update(Exhibition item) {
        for (int i = 0; i < exhibitions.size(); i++) {
            if (exhibitions.get(i).getExhibitionId().equals(item.getExhibitionId())) {
                exhibitions.set(i, item);
                return true;
            }
        }
        return false;
    }

    public Long getCount() {
        return (long)exhibitions.size();
    }
}
