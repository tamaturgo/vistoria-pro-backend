package com.tamaturgo.provistoria.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupabaseLoginResponse {

    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("refresh_token")
    public String refreshToken;

    @JsonProperty("expires_in")
    public int expiresIn;

    @JsonProperty("expires_at")
    public long expiresAt;

    @JsonProperty("token_type")
    public String tokenType;

    public User user;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        public String id;
        public String email;
        public String role;

        @JsonProperty("email_confirmed_at")
        public String emailConfirmedAt;

        @JsonProperty("last_sign_in_at")
        public String lastSignInAt;

        @JsonProperty("created_at")
        public String createdAt;

        @JsonProperty("updated_at")
        public String updatedAt;

        public boolean is_anonymous;

        public AppMetadata app_metadata;
        public UserMetadata user_metadata;
        public Identity[] identities;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AppMetadata {
        public String provider;
        public String[] providers;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserMetadata {
        @JsonProperty("email_verified")
        public boolean emailVerified;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Identity {
        @JsonProperty("identity_id")
        public String identityId;

        public String id;

        @JsonProperty("user_id")
        public String userId;

        public String email;
        public IdentityData identity_data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IdentityData {
        public String email;

        @JsonProperty("email_verified")
        public boolean emailVerified;

        @JsonProperty("phone_verified")
        public boolean phoneVerified;

        public String sub;
    }
}
