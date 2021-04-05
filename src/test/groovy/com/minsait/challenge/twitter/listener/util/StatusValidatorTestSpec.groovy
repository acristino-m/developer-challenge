package com.minsait.challenge.twitter.listener.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
@ActiveProfiles("test")
class StatusValidatorTestSpec extends Specification {

    @Autowired
    StatusValidator validator

    @Unroll
    def 'check status (#lang, #followers) = #result'(String lang, int followers, boolean result) {
        expect:
        validator.checkTweet(lang, followers) == result

        where:
        lang | followers | result
        'es' | 1000      | false
        'es' | 1500      | true
        'fr' | 500       | false
        'fr' | 1600      | true
        'it' | 600       | false
        'it' | 1700      | true
        'en' | 600       | false
        'en' | 1600      | false

    }
}
