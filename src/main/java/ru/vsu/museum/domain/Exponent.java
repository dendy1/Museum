package ru.vsu.museum.domain;

import java.util.Date;

public class Exponent {
    private Long id;
    private String name;
    private String description;
    private Date createDate;
    private Long categoryId;
    private Long locationId;

    public Exponent(Long id, String name, String description, Date createDate, Long categoryId, Long locationId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createDate = createDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
