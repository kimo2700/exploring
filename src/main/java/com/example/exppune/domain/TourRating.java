package com.example.exppune.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class TourRating {

    @EmbeddedId
    private TourRatingPk pk;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

}
