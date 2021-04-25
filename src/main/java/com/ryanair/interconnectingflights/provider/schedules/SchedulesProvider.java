package com.ryanair.interconnectingflights.provider.schedules;

import com.ryanair.interconnectingflights.provider.schedules.domain.Schedules;

public interface SchedulesProvider {
    Schedules getSchedules(String departure, String arrival, String month, String year);
}
