package com.minsait.challenge.twitter.persistence.mapper;

import com.minsait.challenge.twitter.domain.HashtagDto;
import com.minsait.challenge.twitter.persistence.entities.HashtagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    HashtagDto entityToDto(HashtagEntity entity);

}
