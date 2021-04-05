INSERT INTO t_tweets (tweet_id, version, tweet_text, tweet_user, tweet_retweet, tweet_retweet_count, tweet_lang, tweet_created_at, tweet_validated, tweet_validated_at, tweet_validator)
VALUES (1, 0, 'Lorem itsum', 'user1', false, 0, 'es', CURRENT_TIMESTAMP, false, NULL, NULL)
;

INSERT INTO t_tweets (tweet_id, version, tweet_text, tweet_user, tweet_retweet, tweet_retweet_count, tweet_lang, tweet_created_at, tweet_validated, tweet_validated_at, tweet_validator)
VALUES (2, 0, 'Lorem itsum', 'user1', false, 0, 'es', CURRENT_TIMESTAMP, true, CURRENT_TIMESTAMP, 'validator1')
;

INSERT INTO t_tweets (tweet_id, version, tweet_text, tweet_user, tweet_retweet, tweet_retweet_count, tweet_lang, tweet_created_at, tweet_validated, tweet_validated_at, tweet_validator)
VALUES (3, 0, 'Lorem itsum', 'user1', false, 0, 'es', CURRENT_TIMESTAMP, false, NULL, NULL)
;

INSERT INTO t_hashtags (hashtag_id, version, hashtag_name, hashtag_count)
VALUES (1, 0, 'hashtag1', 1)
;

INSERT INTO t_hashtags (hashtag_id, version, hashtag_name, hashtag_count)
VALUES (2, 0, 'hashtag2', 5)
;
