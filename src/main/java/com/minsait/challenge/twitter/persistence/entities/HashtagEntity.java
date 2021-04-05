package com.minsait.challenge.twitter.persistence.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_hashtags")
@Data
public class HashtagEntity {

    public static final int HASHTAG_LENGTH = 128;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Version
    @Column(name = "version", precision = 5)
    private Integer version;

    @Column(name = "hashtag_name", length = HASHTAG_LENGTH)
    private String hashtag;

    @Column(name = "hashtag_count")
    private int count;

}
