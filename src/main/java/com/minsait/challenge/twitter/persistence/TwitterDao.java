package com.minsait.challenge.twitter.persistence;

import com.minsait.challenge.twitter.domain.HashtagDto;
import com.minsait.challenge.twitter.domain.TweetDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TwitterDao {
    List<TweetDto> getTweets();
    List<TweetDto> getTweetsValidatedByUser(String user);
    List<HashtagDto> getHashtags(int number);
    @Transactional
    TweetDto save(TweetDto dto);
    @Transactional
    boolean validateTweet(Long id, String user);
}
