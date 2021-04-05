package com.minsait.challenge.twitter.etc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.openapi")
@Data
public class OpenApiProperties {
    private String title;
    private String version;
    private String description;
    private String termsOfService;
    private String server;
}
