package ru.vsu.museum.persistence.repositories.inMemory;

import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;
import java.util.List;

public class ExponentRepository implements Repository<Exponent> {
    private Long lastId;

    private static ExponentRepository instance;
    public static ExponentRepository getInstance() {
        if (instance == null) {
            instance = new ExponentRepository();
        }
        return instance;
    }
    private ArrayList<Exponent> exponents;

    public ExponentRepository() {
        exponents = new ArrayList<Exponent>();
        for (long i = 0; i < 10; i++)
        {
            exponents.add(new Exponent(i, "Exponent " + i, i % 2, i % 4));
        }
        lastId = getCount();
    }

    @Override
    public List<Exponent> getAll(String where) {
        return null;
    }

    public Exponent getById(Long id) {
        for (Exponent exponent : exponents) {
            if (exponent.getExponentId().equals(id))
                return exponent;
        }
        return null;
    }

    public boolean create(Exponent item) {
        item.setExponentId(++lastId);
        exponents.add(item);
        return true;
    }

    public boolean delete(Long id) {
        for (int i = 0; i < exponents.size(); i++) {
            if (exponents.get(i).getExponentId().equals(id)) {
                exponents.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean update(Exponent item) {
        for (int i = 0; i < exponents.size(); i++) {
            if (exponents.get(i).getExponentId().equals(item.getExponentId())) {
                exponents.set(i, item);
                return true;
            }
        }
        return false;
    }

    public Long getCount() {
        return (long)exponents.size();
    }
}
