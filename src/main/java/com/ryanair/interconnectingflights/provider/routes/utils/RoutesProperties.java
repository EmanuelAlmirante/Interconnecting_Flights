package com.ryanair.interconnectingflights.provider.routes.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("routes")
public class RoutesProperties {
    private String url;
}
