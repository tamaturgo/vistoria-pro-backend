package com.tamaturgo.provistoria.dto.user;

import java.util.UUID;

public record UserMeResponse(
        UUID id,
        String email,
        String fullName,
        String officeName,
        String status
) {

    public static UserMeResponseBuilder builder() {
        return new UserMeResponseBuilder();
    }

    public static class UserMeResponseBuilder {
        private UUID id;
        private String email;
        private String fullName;
        private String officeName;
        private String status;

        public UserMeResponseBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserMeResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserMeResponseBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserMeResponseBuilder officeName(String officeName) {
            this.officeName = officeName;
            return this;
        }

        public UserMeResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        public UserMeResponse build() {
            return new UserMeResponse(id, email, fullName, officeName, status);
        }
    }
}