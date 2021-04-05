package com.minsait.challenge.twitter.controller;

import com.minsait.challenge.twitter.business.TwitterService;
import com.minsait.challenge.twitter.domain.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/twitter")
@RequiredArgsConstructor
public class TwitterController {

    private final TwitterService service;

    @Operation(summary = "Get tweets", description = "Get all saved tweets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tweets saved", content = @Content(schema = @Schema(implementation = TweetResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No tweets already saved", content = @Content(schema = @Schema(implementation = TweetResponseDto.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Callable<ResponseEntity<TweetResponseDto>> getTweets(
            @Parameter(description = "Get tweets validated by this user", required = false, schema = @Schema(implementation = String.class))
            @RequestParam(name = "user", required = false) String user) {

        TweetResponseDto response = new TweetResponseDto();

        List<TweetDto> tweetList;
        if (StringUtils.isEmpty(user)) {
            tweetList = service.getTweets();
        } else {
            tweetList = service.getTweetsValidatedByUser(user);
        }
        if (tweetList != null && !tweetList.isEmpty()) {
            response.setInfo(HttpStatus.OK);
            response.setTweets(tweetList);
        } else {
            response.setInfo(HttpStatus.NOT_FOUND);
        }

        return () -> ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Validate a tweet", description = "Validation a tweet by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tweet found and marked as validated", content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Tweet not found", content = @Content(schema = @Schema(implementation = BaseResponseDto.class)))
    })
    @PutMapping(path = "/{id}/{user}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Callable<ResponseEntity<BaseResponseDto>> validateTweet(
            @Parameter(description = "Tweet identifier", required = true, schema = @Schema(implementation = Long.class))
            @PathVariable(name = "id") Long id,
            @Parameter(description = "Validator", required = true, schema = @Schema(implementation = String.class))
            @PathVariable(name = "user") String user) {
        boolean validated = service.validateTweet(id, user);
        BaseResponseDto response = new BaseResponseDto() {
        };
        if (validated) {
            response.setInfo(HttpStatus.OK);
        } else {
            response.setInfo(HttpStatus.NOT_FOUND);
        }
        return () -> ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Get hashtags", description = "Get hashtags from tweets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hashtags", content = @Content(schema = @Schema(implementation = HashtagResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No hashtags already saved", content = @Content(schema = @Schema(implementation = HashtagResponseDto.class)))
    })
    @GetMapping(path = "/hashtags", produces = MediaType.APPLICATION_JSON_VALUE)
    public Callable<ResponseEntity<HashtagResponseDto>> getHashtags(
            @Parameter(description = "Number of hashtags to retrieve", required = true, schema = @Schema(implementation = Integer.class))
            @RequestParam(name = "number", required = true) int number) {
        HashtagResponseDto response = new HashtagResponseDto();

        List<HashtagDto> hashtags = service.getHashTags(number);
        if (hashtags != null && !hashtags.isEmpty()) {
            response.setInfo(HttpStatus.OK);
            response.setHashtags(hashtags);
        } else {
            response.setInfo(HttpStatus.NOT_FOUND);
        }
        return () -> ResponseEntity.status(response.getCode()).body(response);
    }

}
