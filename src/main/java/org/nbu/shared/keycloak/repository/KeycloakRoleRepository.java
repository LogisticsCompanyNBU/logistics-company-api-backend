package org.nbu.shared.keycloak.repository;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakRoleRepository {

    private Keycloak keycloak;
    @Value("${keycloak-admin-client-configuration.realm}")
    private String realm;

    @Autowired
    public KeycloakRoleRepository(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public RoleRepresentation findRoleByName(String roleName) {
        return keycloak.realm(realm)
                       .roles()
                       .get(roleName)
                       .toRepresentation();
    }
}
