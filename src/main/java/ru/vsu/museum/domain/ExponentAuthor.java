package ru.vsu.museum.domain;

public class ExponentAuthor {
    private Long exponentId;
    private Long authorId;

    public ExponentAuthor(Long exponentId, Long authorId) {
        this.exponentId = exponentId;
        this.authorId = authorId;
    }

    public ExponentAuthor() {
    }

    public Long getExponentId() {
        return exponentId;
    }

    public void setExponentId(Long exponentId) {
        this.exponentId = exponentId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
