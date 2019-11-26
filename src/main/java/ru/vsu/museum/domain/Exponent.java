package ru.vsu.museum.domain;

import java.util.Date;

public class Exponent {
    private Long id;
    private String name;
    private Long categoryId;
    private Long locationId;

    public Exponent(Long id, String name, Long categoryId, Long locationId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.locationId = locationId;
    }

    public Exponent() {
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
