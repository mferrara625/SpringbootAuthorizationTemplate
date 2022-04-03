package com.authenticate.authTemplate.models;

import com.authenticate.authTemplate.auth.User;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import javax.persistence.*;

@Entity
public class Content {

    @Id
    @GeneratedValue
    private Long id;
    private String topic;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIncludeProperties("id")
    private Profile author;
    private String authorName;

    public Content() {
    }

    public Content(Long id, String topic, String text, Profile author, String authorName) {
        this.id = id;
        this.topic = topic;
        this.text = text;
        this.author = author;
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
