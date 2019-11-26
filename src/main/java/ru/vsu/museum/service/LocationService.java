package ru.vsu.museum.service;

import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.Location;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.db.DBExponentRepository;
import ru.vsu.museum.persistence.repositories.db.DBLocationRepository;

import java.util.List;

public class LocationService {
    private Repository<Location> locationRepository = new DBLocationRepository();
    private Repository<Exponent> exponentRepository = new DBExponentRepository();

    public List<Location> getAll() {
        return locationRepository.getAll();
    }

    public Location getById(long id) {
        return locationRepository.getById(id);
    }

    public void deleteById(long id) {
        for (Exponent exponent : exponentRepository.getAll()) {
            if (exponent.getLocationId().equals(id)) {
                Exponent newExponent = exponent;
                newExponent.setLocationId(null);
                exponentRepository.update(newExponent);
            }
        }
        locationRepository.delete(id);
    }

    public void add(Location item) {
        locationRepository.create(item);
    }

    public long getLastId()
    {
        long id = 0;
        for (Location location: locationRepository.getAll()) {
            if (location.getLocationId() > id)
                id = location.getLocationId();
        }
        return id;
    }

    public void update(Location item) {
        locationRepository.update(item);
    }
}
