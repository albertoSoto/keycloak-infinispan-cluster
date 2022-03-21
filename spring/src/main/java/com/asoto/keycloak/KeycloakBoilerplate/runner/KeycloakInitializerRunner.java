package com.asoto.keycloak.KeycloakBoilerplate.runner;

import com.asoto.keycloak.KeycloakBoilerplate.security.WebSecurityConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeycloakInitializerRunner implements CommandLineRunner {

    private static final String EXAMPLE_SERVICE_REALM_NAME = "example-services";
    private static final String EXAMPLE_APP_CLIENT_ID = "example-app";
    private static final List<String> EXAMPLE_APP_ROLES = Arrays.asList(WebSecurityConfig.USER,
            WebSecurityConfig.ROL_MANAGER);
    private static final String EXAMPLE_APP_REDIRECT_URL = "http://localhost:3000/*";
    private static final List<UserPass> LINCE_APP_USERS = Arrays.asList(new UserPass("admin", "admin"),
            new UserPass("user", "user"));

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    private final Keycloak keycloakAdmin;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing '{}' realm in Keycloak ...", EXAMPLE_SERVICE_REALM_NAME);
        try {

            Optional<RealmRepresentation> representationOptional = keycloakAdmin.realms().findAll().stream()
                    .filter(r -> r.getRealm().equals(EXAMPLE_SERVICE_REALM_NAME)).findAny();
            if (representationOptional.isPresent()) {
                log.info("Removing already pre-configured '{}' realm", EXAMPLE_SERVICE_REALM_NAME);
                keycloakAdmin.realm(EXAMPLE_SERVICE_REALM_NAME).remove();
            }

            // Realm
            RealmRepresentation realmRepresentation = new RealmRepresentation();
            realmRepresentation.setRealm(EXAMPLE_SERVICE_REALM_NAME);
            realmRepresentation.setEnabled(true);
            realmRepresentation.setRegistrationAllowed(true);

            // Client
            ClientRepresentation clientRepresentation = new ClientRepresentation();
            clientRepresentation.setClientId(EXAMPLE_APP_CLIENT_ID);
            clientRepresentation.setDirectAccessGrantsEnabled(true);
            clientRepresentation.setDefaultRoles(new String[]{EXAMPLE_APP_ROLES.get(0)});
            clientRepresentation.setPublicClient(true);
            clientRepresentation.setRedirectUris(Collections.singletonList(EXAMPLE_APP_REDIRECT_URL));
            realmRepresentation.setClients(Collections.singletonList(clientRepresentation));

            // Users
            List<UserRepresentation> userRepresentations = LINCE_APP_USERS.stream().map(userPass -> {
                // Client roles
                Map<String, List<String>> clientRoles = new HashMap<>();
                if ("admin".equals(userPass.getUsername())) {
                    clientRoles.put(EXAMPLE_APP_CLIENT_ID, EXAMPLE_APP_ROLES);
                } else {
                    clientRoles.put(EXAMPLE_APP_CLIENT_ID, Collections.singletonList(EXAMPLE_APP_ROLES.get(0)));
                }

                // User Credentials
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                credentialRepresentation.setValue(userPass.getPassword());

                // User
                UserRepresentation userRepresentation = new UserRepresentation();
                userRepresentation.setUsername(userPass.getUsername());
                userRepresentation.setEnabled(true);
                userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
                userRepresentation.setClientRoles(clientRoles);

                return userRepresentation;
            }).collect(Collectors.toList());
            realmRepresentation.setUsers(userRepresentations);

            // Create Realm
            keycloakAdmin.realms().create(realmRepresentation);

            // Testing
            UserPass admin = LINCE_APP_USERS.get(0);
            log.info("Testing getting token for '{}' ...", admin.getUsername());

            Keycloak keycloakMovieApp = KeycloakBuilder.builder().serverUrl(keycloakServerUrl)
                    .realm(EXAMPLE_SERVICE_REALM_NAME).username(admin.getUsername()).password(admin.getPassword())
                    .clientId(EXAMPLE_APP_CLIENT_ID).build();

            log.info("'{}' token: {}", admin.getUsername(), keycloakMovieApp.tokenManager().grantToken().getToken());
            log.info("'{}' initialization completed successfully!", EXAMPLE_SERVICE_REALM_NAME);
        } catch (Exception e) {
            log.error("Error with KC init config", e);
        }

    }


    @Data
    @AllArgsConstructor
    private static class UserPass {
        private String username;
        private String password;
    }

}