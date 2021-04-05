package com.minsait.challenge.twitter.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Tweet response")
public class TweetResponseDto extends BaseResponseDto {

    @Schema(description = "List of tweets")
    private List<TweetDto> tweets;

}
