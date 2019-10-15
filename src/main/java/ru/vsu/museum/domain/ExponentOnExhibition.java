package ru.vsu.museum.domain;

public class ExponentOnExhibition {
    private Long exponentId;
    private Long exhibitionId;

    public ExponentOnExhibition(Long exponentId, Long exhibitionId) {
        this.exponentId = exponentId;
        this.exhibitionId = exhibitionId;
    }

    public ExponentOnExhibition() {
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
