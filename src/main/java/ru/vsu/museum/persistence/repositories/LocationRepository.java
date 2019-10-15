package ru.vsu.museum.persistence.repositories;

import ru.vsu.museum.domain.Location;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;

public class LocationRepository implements Repository<Location> {
    private ArrayList<Location> locations;

    public LocationRepository() {
        locations = new ArrayList<Location>();

        locations.add(new Location(1L, "Location 1", "Address for location 1", "Хранилище"));
        locations.add(new Location(2L, "Location 2", "Address for location 2", "Хранилище"));
        locations.add(new Location(3L, "Зал музея 1", "Зал №1", "Зал"));
        locations.add(new Location(4L, "Зал музея 2", "Зал №2", "Зал"));
    }

    public ArrayList<Location> getAll() {
        return locations;
    }

    public Location getById(Long id) {
        for (Location location : locations) {
            if (location.getId().equals(id))
                return location;
        }
        return null;
    }

    public void create(Location item) {
        locations.add(item);
    }

    public void delete(Long id) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getId().equals(id)) {
                locations.remove(i);
                return;
            }
        }
    }

    public void update(Location item) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getId().equals(item.getId())) {
                locations.remove(i);
                return;
            }
        }
    }
}
