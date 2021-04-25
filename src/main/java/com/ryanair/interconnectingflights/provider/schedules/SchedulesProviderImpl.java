package com.ryanair.interconnectingflights.provider.schedules;

import com.ryanair.interconnectingflights.provider.schedules.domain.Schedules;
import com.ryanair.interconnectingflights.provider.schedules.utils.SchedulesProperties;
import com.ryanair.interconnectingflights.utils.exception.SchedulesProviderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.MessageFormat;

@Service
public class SchedulesProviderImpl implements SchedulesProvider {
    private final WebClient client;
    private final SchedulesProperties properties;

    @Autowired
    public SchedulesProviderImpl(WebClient client, SchedulesProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @Override
    public Schedules getSchedules(String departure, String arrival, String month, String year) {
        final String uri = getSchedulesUri(departure, arrival, month, year);

        Schedules schedules = client.get()
                                    .uri(uri)
                                    .retrieve()
                                    .bodyToMono(Schedules.class)
                                    .blockOptional()
                                    .orElseThrow(SchedulesProviderException::new);

        return schedules;
    }

    private String getSchedulesUri(String departure, String arrival, String month, String year) {
        return MessageFormat.format(properties.getUrl(), departure, arrival, year, month);
    }
}
