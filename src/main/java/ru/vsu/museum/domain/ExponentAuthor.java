package ru.vsu.museum.domain;

public class ExponentAuthor {
    private Long id;
    private Long exponentId;
    private Long authorId;

    public ExponentAuthor(Long id, Long exponentId, Long authorId) {
        this.id = id;
        this.exponentId = exponentId;
        this.authorId = authorId;
    }

    public ExponentAuthor() {
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
