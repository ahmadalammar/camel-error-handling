package com.alammar.apache.camel.errorHandling.config;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CamelConfig {

    @Bean
    public CamelContext initCamelContext(List<RouteBuilder> routeBuilderList) {
        CamelContext camelContext = new DefaultCamelContext();

        routeBuilderList.stream()
                .forEach(routeBuilder -> {
                    try {
                        camelContext.addRoutes(routeBuilder);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        camelContext.start();

        return camelContext;
    }
}
