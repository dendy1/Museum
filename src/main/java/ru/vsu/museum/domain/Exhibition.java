package ru.vsu.museum.domain;

import java.util.Date;

public class Exhibition {
    private Long id;
    private Date date;
    private String name;

    public Exhibition(Long id, Date date, String name) {
        this.id = id;
        this.date = date;
        this.name = name;
    }

    public Exhibition() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
