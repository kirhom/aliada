package com.aliada.aliadaback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Search {
    public static int AWAITING_RESPONSE = 0;
    public static int AWAITING_CONFIRMATION = 1;
    public static int IN_PROGRESS = 2;
    public static int DONE = 3;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String reason;

    private int status;

    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "search")
    private Set<Answer> answers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
}