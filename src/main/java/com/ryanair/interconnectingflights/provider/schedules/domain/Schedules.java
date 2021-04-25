package com.ryanair.interconnectingflights.provider.schedules.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedules {
    private Integer month;
    @JsonProperty(value = "days")
    private List<Days> daysList;
}
