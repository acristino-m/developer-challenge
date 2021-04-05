package com.minsait.challenge.twitter.listener.config;

import com.minsait.challenge.twitter.business.TwitterService;
import com.minsait.challenge.twitter.listener.TwitterStatusListener;
import com.minsait.challenge.twitter.listener.mapper.StatusMapper;
import com.minsait.challenge.twitter.listener.util.StatusValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Configuration
@ConditionalOnProperty(name = "application.twitter.stream.enabled", havingValue = "true")
@Slf4j
public class StreamConfiguration {

    @Autowired
    private StatusMapper mapper;

    @Autowired
    private TwitterService service;

    @Autowired
    private StatusValidator validator;

    @EventListener(ApplicationReadyEvent.class)
    public void launchListener() {
        StatusListener listener = new TwitterStatusListener(mapper, validator, service);

        TwitterStream stream = new TwitterStreamFactory().getInstance();
        stream.addListener(listener);
        stream.sample();
    }

}
