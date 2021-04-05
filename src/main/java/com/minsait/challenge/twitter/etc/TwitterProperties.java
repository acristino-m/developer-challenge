package com.minsait.challenge.twitter.etc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "application")
@Data
public class TwitterProperties {

    private int followers;
    private List<String> languages;

}
