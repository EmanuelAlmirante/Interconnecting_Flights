package com.ryanair.interconnectingflights.provider.routes;

import com.ryanair.interconnectingflights.provider.routes.domain.Routes;
import com.ryanair.interconnectingflights.provider.routes.utils.RoutesProperties;
import com.ryanair.interconnectingflights.utils.exception.RoutesProviderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RoutesProviderImpl implements RoutesProvider {
    private static final String OPERATOR = "RYANAIR";

    private final WebClient client;
    private final RoutesProperties properties;

    @Autowired
    public RoutesProviderImpl(WebClient client, RoutesProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @Override
    public List<Routes> getRoutes(String departure, String arrival) {
        List<Routes> routes = client.get()
                                    .uri(properties.getUrl())
                                    .retrieve()
                                    .bodyToMono(new ParameterizedTypeReference<List<Routes>>() {
                                    })
                                    .blockOptional()
                                    .orElseThrow(RoutesProviderException::new);

        filterRoutes(routes, departure, arrival);

        return routes;
    }

    private void filterRoutes(List<Routes> routes, String departure, String arrival) {
        filterRoutesByConnectingAirport(routes);
        filterRoutesByOperator(routes);
        filterRoutesByIncorrectOnes(routes, departure, arrival);
    }

    private void filterRoutesByConnectingAirport(List<Routes> routes) {
        routes.removeIf(route -> route.getConnectingAirport() != null);
    }

    private void filterRoutesByOperator(List<Routes> routes) {
        routes.removeIf(route -> !route.getOperator().equals(OPERATOR));
    }

    private void filterRoutesByIncorrectOnes(List<Routes> routes, String departure, String arrival) {
        routes.removeIf(route -> !route.getAirportFrom().equals(departure) && !route.getAirportTo().equals(arrival));
    }
}
