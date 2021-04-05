package com.minsait.challenge.twitter.business

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
class TwitterServiceTestSpec extends Specification {

    @Autowired
    TwitterService service

    @Unroll
    def 'get tweets'() {
        expect:
        service.getTweets().size() > 0
    }

    @Unroll
    def 'get tweets validated by user'(String user, int numTweets) {
        expect:
        service.getTweetsValidatedByUser(user).size() == numTweets

        where:
        user         | numTweets
        'validator1' | 1
        'validator2' | 0
    }
}
