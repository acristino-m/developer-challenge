package com.minsait.challenge.twitter.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Hashtag information")
public class HashtagDto implements Serializable {

    @Schema(description = "Hashtag")
    private String hashtag;

    @Schema(description = "Hashtag ocurrencies")
    private int count;

}
