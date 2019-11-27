package ru.vsu.museum.service;

import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.Location;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.db.DBRepository;

import java.util.List;

public class LocationService {
    private Repository<Location> locationRepository =
            new DBRepository<Location>(Location.class, "location", "locationId");
    private Repository<Exponent> exponentRepository =
            new DBRepository<Exponent>(Exponent.class, "exponent", "exponentId");

    public List<Location> getAll() {
        return locationRepository.getAll(null);
    }

    public Location getById(long id) {
        return locationRepository.getById(id);
    }

    public void deleteById(long locationId) {
        for (Exponent exponent : exponentRepository.getAll("locationId="+locationId)) {
            exponent.setLocationId(null);
            exponentRepository.update(exponent);
        }
        locationRepository.delete(locationId);
    }

    public void add(Location item) {
        locationRepository.create(item);
    }

    public void update(Location item) {
        locationRepository.update(item);
    }

    public Long getCount() {
        return locationRepository.getCount();
    }
}
