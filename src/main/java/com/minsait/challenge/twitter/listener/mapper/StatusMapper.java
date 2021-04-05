package com.minsait.challenge.twitter.listener.mapper;

import com.minsait.challenge.twitter.domain.TweetDto;
import org.mapstruct.*;
import twitter4j.HashtagEntity;
import twitter4j.Status;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class StatusMapper {

    @Mappings({
            @Mapping(source = "user.screenName", target = "user")
    })
    public abstract TweetDto statusToDto(Status status);

    @AfterMapping
    protected void setInternalValues(Status status, @MappingTarget TweetDto dto) {
        if (status.getGeoLocation() != null) {
            dto.setLongitude(status.getGeoLocation().getLongitude());
            dto.setLatitude(status.getGeoLocation().getLatitude());
        }
        if (status.getHashtagEntities() != null && status.getHashtagEntities().length > 0) {
            Set<String> hashTags = new HashSet<>();
            for (HashtagEntity hashtagEntity : status.getHashtagEntities()) {
                hashTags.add(hashtagEntity.getText());
            }
            dto.setHashTags(hashTags);
        }
    }

}
