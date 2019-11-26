package ru.vsu.museum.persistence.repositories.inMemory;

import ru.vsu.museum.domain.Location;
import ru.vsu.museum.persistence.Repository;

import java.util.ArrayList;

public class LocationRepository implements Repository<Location> {
    private static LocationRepository instance;
    public static LocationRepository getInstance() {
        if (instance == null) {
            instance = new LocationRepository();
        }
        return instance;
    }
    private ArrayList<Location> locations;

    public LocationRepository() {
        locations = new ArrayList<Location>();
        locations.add(new Location(0L, "Location 1"));
        locations.add(new Location(1L, "Location 2"));
        locations.add(new Location(2L, "Зал музея 1"));
        locations.add(new Location(3L, "Зал музея 2"));
    }

    public ArrayList<Location> getAll() {
        return locations;
    }

    public Location getById(Long id) {
        for (Location location : locations) {
            if (location.getLocationId().equals(id))
                return location;
        }
        return null;
    }

    public boolean create(Location item) {
        locations.add(item);
        return true;
    }

    public boolean delete(Long id) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getLocationId().equals(id)) {
                locations.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean update(Location item) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getLocationId().equals(item.getLocationId())) {
                locations.set(i, item);
                return true;
            }
        }
        return false;
    }

    public Long getCount() {
        return (long)locations.size();
    }
}
