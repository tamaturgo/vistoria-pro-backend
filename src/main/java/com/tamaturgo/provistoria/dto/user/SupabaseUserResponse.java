package com.tamaturgo.provistoria.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupabaseUserResponse {

    public String id;
    public String aud;
    public String role;
    public String email;
    public String email_confirmed_at;
    public String phone;
    public String confirmed_at;
    public String recovery_sent_at;
    public String last_sign_in_at;
    public Map<String, Object> app_metadata;
    public Map<String, Object> user_metadata;
    public List<Identity> identities;
    public String created_at;
    public String updated_at;
    public boolean is_anonymous;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Identity {
        public String identity_id;
        public String id;
        public String user_id;
        public Map<String, Object> identity_data;
        public String provider;
        public String last_sign_in_at;
        public String created_at;
        public String updated_at;
        public String email;
    }
}
