package com.minsait.challenge.twitter.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class TwitterDaoTestSpec extends Specification {

    @Autowired
    TwitterDao twitterDao

    @Unroll
    def 'get tweets()'() {
        expect:
        twitterDao.getTweets().size() == 3
    }

    @Unroll
    def 'get hashtags(#number) = #count'(int number, int count) {
        expect:
        twitterDao.getHashtags(number).size() == count

        where:
        number | count
        1      | 1
        10     | 2
    }

}