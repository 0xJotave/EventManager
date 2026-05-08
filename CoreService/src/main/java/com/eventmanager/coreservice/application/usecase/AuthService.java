package com.eventmanager.coreservice.application.usecase;

import com.eventmanager.coreservice.adapter.dto.auth.LoginRequestDTO;
import com.eventmanager.coreservice.adapter.dto.auth.LoginResponseDTO;
import com.eventmanager.coreservice.adapter.dto.auth.UserRegistrationDTO;
import com.eventmanager.coreservice.application.port.inbound.AuthServicePort;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServicePort {

    private final Keycloak keycloak;

    @Value("${app.keycloak.realm}")
    private String realmName;

    @Value("${app.keycloak.server-url}")
    private String serverUrl;

    @Value("${app.keycloak.client-id}")
    private String clientId;

    @Value("${app.keycloak.client-secret}")
    private String clientSecret;

    @Override
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

        try (Response response = keycloak.realm(realmName).users().create(user)) {
            if (response.getStatus() != 201 && response.getStatus() != 200) {
                throw new RuntimeException("Falha ao registrar usuário no Keycloak");
            }
        }
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        try (Keycloak userKeycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realmName)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(dto.username())
                .password(dto.password())
                .build()) {

            var token = userKeycloak.tokenManager().getAccessToken();

            return new LoginResponseDTO(
                    token.getToken(),
                    token.getRefreshToken(),
                    token.getExpiresIn()
            );
        } catch (Exception e) {
            throw new RuntimeException("Login falhou");
        }
    }
}