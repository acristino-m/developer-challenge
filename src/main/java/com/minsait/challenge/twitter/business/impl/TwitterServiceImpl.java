package com.minsait.challenge.twitter.business.impl;

import com.minsait.challenge.twitter.business.TwitterService;
import com.minsait.challenge.twitter.domain.HashtagDto;
import com.minsait.challenge.twitter.domain.TweetDto;
import com.minsait.challenge.twitter.persistence.TwitterDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterServiceImpl implements TwitterService {

    private final TwitterDao dao;

    @Override
    public List<TweetDto> getTweets() {
        return dao.getTweets();
    }

    @Override
    public List<TweetDto> getTweetsValidatedByUser(String user) {
        return dao.getTweetsValidatedByUser(user);
    }

    @Override
    public void save(TweetDto dto) {
        dao.save(dto);
    }

    @Override
    public boolean validateTweet(Long id, String user) {
        return dao.validateTweet(id, user);
    }

    @Override
    public List<HashtagDto> getHashTags(int number) {
        return dao.getHashtags(number);
    }
}
