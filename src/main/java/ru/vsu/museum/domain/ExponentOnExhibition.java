package ru.vsu.museum.domain;

public class ExponentOnExhibition {
    private Long id;
    private Long exhibitionId;
    private Long exponentId;

    public ExponentOnExhibition(Long id, Long exhibitionId, Long exponentId) {
        this.id = id;
        this.exhibitionId = exhibitionId;
        this.exponentId = exponentId;
    }

    public ExponentOnExhibition() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExponentId() {
        return exponentId;
    }

    public void setExponentId(Long exponentId) {
        this.exponentId = exponentId;
    }

    public Long getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Long exhibitionId) {
        this.exhibitionId = exhibitionId;
    }
}
