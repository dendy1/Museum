package ru.vsu.museum.persistence.repositories.inMemory;

import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;
import java.util.Date;

public class ExponentRepository implements Repository<Exponent> {
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
    }

    public ArrayList<Exponent> getAll() {
        return exponents;
    }

    public Exponent getById(Long id) {
        for (Exponent exponent : exponents) {
            if (exponent.getId().equals(id))
                return exponent;
        }
        return null;
    }

    public Boolean create(Exponent item) {
        exponents.add(item);
        return true;
    }

    public Boolean delete(Long id) {
        for (int i = 0; i < exponents.size(); i++) {
            if (exponents.get(i).getId().equals(id)) {
                exponents.remove(i);
                return true;
            }
        }
        return false;
    }

    public Boolean update(Exponent item) {
        for (int i = 0; i < exponents.size(); i++) {
            if (exponents.get(i).getId().equals(item.getId())) {
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
