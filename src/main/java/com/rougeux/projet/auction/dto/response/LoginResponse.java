package com.rougeux.projet.auction.dto.response;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class LoginResponse {

    private String username;
    private String token;
    private Collection<GrantedAuthority> roles;

    public LoginResponse(UserDetails userDetails, String token) {
        this.username = userDetails.getUsername();
        this.token = token;
        this.roles = new ArrayList<>(userDetails.getAuthorities());
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public Collection<GrantedAuthority> getRoles() {
        return roles;
    }
    public void setRoles(Collection<GrantedAuthority> roles) {
        this.roles = roles;
    }
}
