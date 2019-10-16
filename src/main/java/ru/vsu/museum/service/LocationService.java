package ru.vsu.museum.service;

import ru.vsu.museum.domain.Location;
import ru.vsu.museum.persistence.repositories.LocationRepository;

import java.util.ArrayList;

public class LocationService {
    private LocationRepository locationRepository = LocationRepository.getInstance();

    public ArrayList<Location> getAll() {
        return locationRepository.getAll();
    }

    public Location getById(long id) {
        return locationRepository.getById(id);
    }

    public void deleteById(long id) {
        locationRepository.delete(id);
    }

    public void add(Location item) {
        locationRepository.create(item);
    }

    public long getLastId()
    {
        long id = 0;
        for (Location location: locationRepository.getAll()) {
            if (location.getId() > id)
                id = location.getId();
        }
        return id;
    }
}
