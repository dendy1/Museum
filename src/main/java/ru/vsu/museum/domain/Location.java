package ru.vsu.museum.domain;

public class Location {
    private Long id;
    private String name;

    public Location(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
