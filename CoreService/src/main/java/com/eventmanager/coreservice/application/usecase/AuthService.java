package com.eventmanager.coreservice.application.usecase;

import com.eventmanager.coreservice.adapter.dto.LoginRequestDTO;
import com.eventmanager.coreservice.adapter.dto.UserRegistrationDTO;
import com.eventmanager.coreservice.application.port.inbound.AuthServicePort;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServicePort {

    private final Keycloak keycloak;
    private final String realmName = "event-manager-realm";

    public void registerUser(UserRegistrationDTO dto) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(dto.username());
        user.setAttributes(Map.of("fullName", Collections.singletonList(dto.fullName())));
        user.setEmail(dto.email());
        user.setEmailVerified(true);

        user.setRequiredActions(Collections.emptyList());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(dto.password());
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        Response response = keycloak.realm(realmName).users().create(user);

        if (response.getStatus() == 201) {
            if (response.getStatus() == 201 || response.getStatus() == 200) {
                String userId;

                if (response.getLocation() != null) {
                    userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
                } else {
                    userId = keycloak.realm(realmName).users().search(dto.username()).get(0).getId();
                }

                String roleToAssign = "admin".equalsIgnoreCase(dto.username()) ? "ADMIN" : "CUSTOMER";

                try {
                    var roleRepresentation = keycloak.realm(realmName)
                            .roles()
                            .get(roleToAssign)
                            .toRepresentation();

                    keycloak.realm(realmName)
                            .users()
                            .get(userId)
                            .roles()
                            .realmLevel()
                            .add(Collections.singletonList(roleRepresentation));

                    System.out.println("Role " + roleToAssign + " atribuída ao usuário: " + dto.username());

                } catch (Exception e) {
                    throw new RuntimeException("Erro ao atribuir role " + roleToAssign + ": " + e.getMessage());
                }
            }
        }
    }

    public Map<String, Object> login(LoginRequestDTO dto) {
        try (Keycloak userKeycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8082")
                .realm(realmName)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("gateway-service")
                .clientSecret("SqSFG41gbinOhUntX62K0GwPlgr5v26h")
                .username(dto.username())
                .password(dto.password())
                .build()) {

            var token = userKeycloak.tokenManager().getAccessToken();

            return Map.of(
                    "access_token", token.getToken(),
                    "expires_in", token.getExpiresIn(),
                    "refresh_token", token.getRefreshToken(),
                    "token_type", token.getTokenType()
            );
        } catch (Exception e) {
            throw new RuntimeException("Login falhou: Usuário ou senha inválidos");
        }
    }
}