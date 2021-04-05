package com.minsait.challenge.twitter.persistence.repositories;

import com.minsait.challenge.twitter.persistence.entities.TweetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, Long> {

    List<TweetEntity> findByValidatorAndValidatedIsTrue(String user);

    Optional<TweetEntity> findByIdAndValidatedIsFalse(Long id);

}
