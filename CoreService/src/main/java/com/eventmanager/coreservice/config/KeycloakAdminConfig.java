package com.eventmanager.coreservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {

    @Value("${app.keycloak.server-url}")
    private String serverUrl;

    @Value("${app.keycloak.admin-user}")
    private String adminUser;

    @Value("${app.keycloak.admin-pass}")
    private String adminPass;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .clientId("admin-cli")
                .grantType(OAuth2Constants.PASSWORD)
                .username(adminUser)
                .password(adminPass)
                .build();
    }
}
