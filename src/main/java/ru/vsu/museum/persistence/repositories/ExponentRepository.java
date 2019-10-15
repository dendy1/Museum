package ru.vsu.museum.persistence.repositories;

import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;
import java.util.Date;

public class ExponentRepository implements Repository<Exponent> {
    private ArrayList<Exponent> exponents;

    public ExponentRepository() {
        exponents = new ArrayList<Exponent>();
        for (long i = 1; i < 10; i++)
        {
            exponents.add(new Exponent(i, "Exponent " + i, "Description for exponent " + i, 1, new Date(), i % 3, i % 5));
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

    public void create(Exponent item) {
        exponents.add(item);
    }

    public void delete(Long id) {
        for (int i = 0; i < exponents.size(); i++) {
            if (exponents.get(i).getId().equals(id)) {
                exponents.remove(i);
                return;
            }
        }
    }

    public void update(Exponent item) {
        for (int i = 0; i < exponents.size(); i++) {
            if (exponents.get(i).getId().equals(item.getId())) {
                exponents.remove(i);
                return;
            }
        }
    }
}
