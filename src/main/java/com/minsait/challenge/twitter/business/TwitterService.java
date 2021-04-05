package com.minsait.challenge.twitter.business;

import com.minsait.challenge.twitter.domain.HashtagDto;
import com.minsait.challenge.twitter.domain.TweetDto;

import java.util.List;

public interface TwitterService {
    List<TweetDto> getTweets();
    List<TweetDto> getTweetsValidatedByUser(String user);
    void save(TweetDto dto);
    boolean validateTweet(Long id, String user);
    List<HashtagDto> getHashTags(int number);
}
