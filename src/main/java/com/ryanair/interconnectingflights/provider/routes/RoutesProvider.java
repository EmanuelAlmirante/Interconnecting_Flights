package com.ryanair.interconnectingflights.provider.routes;

import com.ryanair.interconnectingflights.provider.routes.domain.Routes;

import java.util.List;

public interface RoutesProvider {
    List<Routes> getRoutes(String departure, String arrival);
}
