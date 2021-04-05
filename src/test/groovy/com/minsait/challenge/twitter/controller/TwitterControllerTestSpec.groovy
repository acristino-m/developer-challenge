package com.minsait.challenge.twitter.controller


import com.minsait.challenge.twitter.domain.TweetResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class TwitterControllerTestSpec extends Specification {

    @Autowired
    TestRestTemplate testRestTemplate

    @Unroll
    def 'ask for tweets = #statusCode / #numTweets'(int numTweets, int statusCode) {
        given:
        HttpHeaders httpHeaders = new HttpHeaders()

        httpHeaders.setContentType(MediaType.APPLICATION_JSON)
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON))

        HttpEntity<?> request = new HttpEntity<>(httpHeaders)

        when: 'request the tweet list'
        def result = testRestTemplate.exchange('/twitter', HttpMethod.GET, request, TweetResponseDto.class)

        then:
        result.statusCode.value == statusCode
        result.statusCode.is2xxSuccessful() && result.body.tweets.size() == numTweets

        where:
        numTweets | statusCode
        3         | 200
    }

    @Unroll
    def 'ask for tweets per validator (#validator) = #statusCode / #numTweets'(String validator, int numTweets, int statusCode) {
        given:
        HttpHeaders httpHeaders = new HttpHeaders()

        httpHeaders.setContentType(MediaType.APPLICATION_JSON)
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON))

        HttpEntity<?> request = new HttpEntity<>(httpHeaders)

        when: 'request the tweet list'
        def result = testRestTemplate.exchange('/twitter?user={user}', HttpMethod.GET, request, TweetResponseDto.class, validator)

        then:
        result.statusCode.value == statusCode
        (result.statusCode.is2xxSuccessful() && result.body.tweets.size() == numTweets) || !result.statusCode.is2xxSuccessful()

        where:
        validator    | numTweets | statusCode
        'validator1' | 1         | 200
        'validator2' | 1         | 404
    }

}
