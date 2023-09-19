package com.wex.transactions.configurations;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api") // Define a group name
                .pathsToMatch("/api/public/**") // Specify the paths to include in this group
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-api") // Define another group name
                .pathsToMatch("/**") // Specify the paths to include in this group
                .build();
    }
}