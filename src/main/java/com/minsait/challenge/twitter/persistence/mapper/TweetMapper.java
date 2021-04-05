package com.minsait.challenge.twitter.persistence.mapper;

import com.minsait.challenge.twitter.domain.TweetDto;
import com.minsait.challenge.twitter.persistence.entities.TweetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TweetMapper {

    public abstract TweetEntity dtoToEntity(TweetDto dto);

    public abstract TweetDto entityToDto(TweetEntity entity);

}
