package com.minsait.challenge.twitter.listener;

import com.minsait.challenge.twitter.business.TwitterService;
import com.minsait.challenge.twitter.domain.TweetDto;
import com.minsait.challenge.twitter.listener.mapper.StatusMapper;
import com.minsait.challenge.twitter.listener.util.StatusValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

@RequiredArgsConstructor
@Slf4j
public class TwitterStatusListener implements StatusListener {

    private final StatusMapper mapper;
    private final StatusValidator validator;
    private final TwitterService service;

    @Override
    public void onStatus(Status status) {
        if (validator.checkTweet(status.getLang(), status.getUser().getFollowersCount())) {
            TweetDto dto = mapper.statusToDto(status);
            log.debug("Salvando tweet: {}", status);
            service.save(dto);
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {

    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {
    }

    @Override
    public void onException(Exception e) {
        log.error("StatusListener exception: {}", e.getMessage(), e);
    }

}
