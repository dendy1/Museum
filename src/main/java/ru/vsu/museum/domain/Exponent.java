package ru.vsu.museum.domain;

public class Exponent {
    private Long exponentId;
    private String name;
    private Long categoryId;
    private Long locationId;

    public Exponent(Long exponentId, String name, Long categoryId, Long locationId) {
        this.exponentId = exponentId;
        this.name = name;
        this.categoryId = categoryId;
        this.locationId = locationId;
    }

    public Exponent() {
    }

    public Long getExponentId() {
        return exponentId;
    }

    public void setExponentId(Long exponentId) {
        this.exponentId = exponentId;
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
