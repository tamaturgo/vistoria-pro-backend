package com.tamaturgo.provistoria.dto.user;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String fullName,
        String officeName,
        String status,
        String role,
        String createdAt,
        String updatedAt,
        String accessToken,
        String refreshToken,
        long expiresAt
) {

    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }

    public static class UserResponseBuilder {
        private UUID id;
        private String email;
        private String fullName;
        private String officeName;
        private String status;
        private String role;
        private String createdAt;
        private String updatedAt;
        private String accessToken;
        private String refreshToken;
        private long expiresAt;

        public UserResponseBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserResponseBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserResponseBuilder officeName(String officeName) {
            this.officeName = officeName;
            return this;
        }

        public UserResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        public UserResponseBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserResponseBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserResponseBuilder updatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }
        public UserResponseBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }
        public UserResponseBuilder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }
        public UserResponseBuilder expiresAt(long expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(id, email, fullName, officeName, status, role, createdAt, updatedAt, accessToken, refreshToken, expiresAt);
        }
    }
}