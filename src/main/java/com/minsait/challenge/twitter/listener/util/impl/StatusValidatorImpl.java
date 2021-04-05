package com.minsait.challenge.twitter.listener.util.impl;

import com.minsait.challenge.twitter.etc.TwitterProperties;
import com.minsait.challenge.twitter.listener.util.StatusValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusValidatorImpl implements StatusValidator {

    private final TwitterProperties properties;

    @Override
    public boolean checkTweet(String lang, int followers) {
        return (followers >= properties.getFollowers() && properties.getLanguages().contains(lang));
    }

}
