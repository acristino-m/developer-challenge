package com.minsait.challenge.twitter.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_tweets")
@Data
public class TweetEntity {

    public static final int TWEET_TEXT_LENGTH = 1024;
    public static final int TWEET_USER_LENGTH = 32;
    public static final int TWEET_LANG_LENGTH = 16;
    public static final int TWEET_VALIDATOR_LENGTH = 32;

    @Id
    @Column(name = "tweet_id")
    private Long id;

    @Version
    @Column(name = "version", precision = 5)
    private Integer version;

    @Column(name = "tweet_text", length = TWEET_TEXT_LENGTH)
    private String text;

    @Column(name = "tweet_user", length = TWEET_USER_LENGTH)
    private String user;

    @Column(name = "tweet_retweet")
    private boolean retweet;

    @Column(name = "tweet_retweet_count")
    private int retweetCount;

    @Column(name = "tweet_lang", length = TWEET_LANG_LENGTH)
    private String lang;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tweet_created_at")
    private Date createdAt;

    @Column(name = "tweet_validated")
    private boolean validated;

    @Column(name = "tweet_validated_at")
    private Date validatedAt;

    @Column(name = "tweet_validator", length = TWEET_VALIDATOR_LENGTH)
    private String validator;

    @Column(name = "tweet_latitude")
    private Double latitude;

    @Column(name = "tweet_longitude")
    private Double longitude;

}
