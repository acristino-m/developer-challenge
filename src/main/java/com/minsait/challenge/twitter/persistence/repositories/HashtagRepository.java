package com.minsait.challenge.twitter.persistence.repositories;

import com.minsait.challenge.twitter.persistence.entities.HashtagEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<HashtagEntity, Long> {

    Optional<HashtagEntity> findByHashtag(String hashTag);

    List<HashtagEntity> findByOrderByCountDescHashtagAsc(Pageable pageable);

}
