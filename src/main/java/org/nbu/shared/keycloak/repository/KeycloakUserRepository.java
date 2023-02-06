package org.nbu.shared.keycloak.repository;

import java.util.List;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.Builder;
import lombok.Getter;

@Component
public class KeycloakUserRepository {

    private Keycloak keycloak;
    @Value("${keycloak-admin-client-configuration.realm}")
    private String realm;

    @Autowired
    public KeycloakUserRepository(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public Response createUser(UserRequest userRequest) {
        CredentialRepresentation passwordRepresentation = getCredentialRepresentation(userRequest.getPassword());
        UserRepresentation userRepresentation = getUserRepresentation(userRequest.getUsername(), passwordRepresentation);
        return keycloak.realm(realm)
                       .users()
                       .create(userRepresentation);
    }

    private CredentialRepresentation getCredentialRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    private UserRepresentation getUserRepresentation(String username, CredentialRepresentation credentialRepresentation) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(username);
        newUser.setCredentials(List.of(credentialRepresentation));
        newUser.setEnabled(true);
        newUser.setEmailVerified(true);
        return newUser;
    }

    public void assignRole(String userId, RoleRepresentation roleRepresentation) {
        keycloak.realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(roleRepresentation));
    }

    public void deleteByUsername(String username) {
        List<UserRepresentation> foundUsers = findByUsername(username);
        Assert.isTrue(foundUsers.size() == 1, "Only one user is expected with the given username");
        UserRepresentation userRepresentation = foundUsers.get(0);
        keycloak.realm(realm)
                .users()
                .delete(userRepresentation.getId());
    }

    public List<UserRepresentation> findByUsername(String username) {
        return keycloak.realm(realm)
                       .users()
                       .search(username);
    }

    @Builder
    @Getter
    public static class UserRequest {
        private String username;
        private String password;
    }
}
