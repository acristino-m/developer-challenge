package com.minsait.challenge.twitter.persistence.impl;

import com.minsait.challenge.twitter.domain.HashtagDto;
import com.minsait.challenge.twitter.domain.TweetDto;
import com.minsait.challenge.twitter.persistence.TwitterDao;
import com.minsait.challenge.twitter.persistence.entities.HashtagEntity;
import com.minsait.challenge.twitter.persistence.mapper.HashtagMapper;
import com.minsait.challenge.twitter.persistence.mapper.TweetMapper;
import com.minsait.challenge.twitter.persistence.repositories.HashtagRepository;
import com.minsait.challenge.twitter.persistence.entities.TweetEntity;
import com.minsait.challenge.twitter.persistence.repositories.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TwitterDaoImpl implements TwitterDao {

    private final TweetMapper tweetMapper;
    private final HashtagMapper hashtagMapper;
    private final TweetRepository tweetRepository;
    private final HashtagRepository hashtagRepository;

    @Override
    public List<TweetDto> getTweets() {
        return tweetRepository.findAll().stream().map(tweetMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<TweetDto> getTweetsValidatedByUser(String user) {
        return tweetRepository.findByValidatorAndValidatedIsTrue(user).stream().map(tweetMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public boolean validateTweet(Long id, String user) {
        boolean result = false;

        Optional<TweetEntity> optionalEntity = tweetRepository.findByIdAndValidatedIsFalse(id);
        if (optionalEntity.isPresent()) {
            TweetEntity entity = optionalEntity.get();
            entity.setValidatedAt(new Date());
            entity.setValidated(true);
            entity.setValidator(user);
            tweetRepository.save(entity);
            result = true;
        }

        return result;
    }

    private void findHashtagAndIncrement(String hashTag) {
        Optional<HashtagEntity> optionalHashtagEntity = hashtagRepository.findByHashtag(hashTag);
        HashtagEntity hashtagEntity;
        if (optionalHashtagEntity.isPresent()) {
            hashtagEntity = optionalHashtagEntity.get();
            hashtagEntity.setCount(hashtagEntity.getCount() + 1);
        } else {
            hashtagEntity = new HashtagEntity();
            hashtagEntity.setHashtag(hashTag);
            hashtagEntity.setCount(1);
        }
        hashtagRepository.save(hashtagEntity);
    }

    @Override
    public TweetDto save(TweetDto dto) {
        if (dto != null) {
            TweetEntity entity = tweetMapper.dtoToEntity(dto);
            if (dto.getHashTags() != null) {
                dto.getHashTags().stream().forEach(this::findHashtagAndIncrement);
            }
            tweetRepository.save(entity);
            return tweetMapper.entityToDto(entity);
        } else {
            return null;
        }
    }

    @Override
    public List<HashtagDto> getHashtags(int number) {
        return hashtagRepository.findByOrderByCountDescHashtagAsc(PageRequest.of(0, number)).stream()
                .map(hashtagMapper::entityToDto).collect(Collectors.toList());
    }

}
