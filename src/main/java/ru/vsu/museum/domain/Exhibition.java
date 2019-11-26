package ru.vsu.museum.domain;

import java.util.Date;

public class Exhibition {
    private Long exhibitionId;
    private Date date;
    private String name;

    public Exhibition(Long exhibitionId, Date date, String name) {
        this.exhibitionId = exhibitionId;
        this.date = date;
        this.name = name;
    }

    public Exhibition() {
    }

    public Long getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Long exhibitionId) {
        this.exhibitionId = exhibitionId;
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
