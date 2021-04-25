package com.ryanair.interconnectingflights.config;

import com.ryanair.interconnectingflights.provider.routes.utils.RoutesProperties;
import com.ryanair.interconnectingflights.provider.schedules.utils.SchedulesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties({RoutesProperties.class, SchedulesProperties.class})
public class InterconnectingFlightsConfiguration {
    private static final Integer MAX_IN_MEMORY_SIZE = 16 * 1024 * 1024;

    @Bean
    public WebClient getWebClientBuilder() {
        return WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                                                                        .codecs(configurer -> configurer.defaultCodecs()
                                                                                                        .maxInMemorySize(
                                                                                                                MAX_IN_MEMORY_SIZE))
                                                                        .build())
                        .build();
    }
}
