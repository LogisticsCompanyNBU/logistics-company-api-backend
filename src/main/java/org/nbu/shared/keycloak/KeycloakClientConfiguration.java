package org.nbu.shared.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientConfiguration {

    private static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";

    @Value("${keycloak-admin-client-configuration.server-url}")
    private String authenticationUrl;

    @Value("${keycloak-admin-client-configuration.realm}")
    private String realm;

    @Value("${keycloak-admin-client-configuration.client-id}")
    private String clientId;

    @Value("${keycloak-admin-client-configuration.secret}")
    private String clientSecretKey;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                              .grantType(CLIENT_CREDENTIALS_GRANT_TYPE)
                              .serverUrl(authenticationUrl)
                              .realm(realm)
                              .clientId(clientId)
                              .clientSecret(clientSecretKey)
                              .build();
    }

}
