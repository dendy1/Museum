package ru.vsu.museum.domain;

public class ExponentOnExhibition {
    private Long id;
    private Long exponentId;
    private Long exhibitionId;

    public ExponentOnExhibition(Long id, Long exponentId, Long exhibitionId) {
        this.id = id;
        this.exponentId = exponentId;
        this.exhibitionId = exhibitionId;
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
