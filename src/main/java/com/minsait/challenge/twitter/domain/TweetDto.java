package com.minsait.challenge.twitter.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Tweet information")
public class TweetDto implements Serializable {

    @Schema(description = "Tweet identifier")
    private Long id;

    @Schema(description = "Tweet text")
    private String text;

    @Schema(description = "User that sent the tweet")
    private String user;

    @Schema(description = "If tweet is a retweet")
    private boolean retweet;

    @Schema(description = "Number of retweets")
    private int retweetCount;

    @Schema(description = "Tweet language")
    private String lang;

    @Schema(description = "Date where tweet was created")
    private Date createdAt;

    @Schema(description = "If tweet is validated")
    private boolean validated;

    @Schema(description = "Date where tweet was validated")
    private Date validatedAt;

    @Schema(description = "Tweet validator")
    private String validator;

    @Schema(description = "Hashtags (used internally)")
    @Hidden
    private Set<String> hashTags;

    @Schema(description = "GeoLocation - Latitude")
    private Double latitude;

    @Schema(description = "GeoLocation - Longitude")
    private Double longitude;

}
