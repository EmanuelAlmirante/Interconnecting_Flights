package com.ryanair.interconnectingflights.provider.schedules.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("schedules")
public class SchedulesProperties {
    private String url;
}
