package com.learn.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class UserDetails {

    private Collection<? extends GrantedAuthority> authorities;

    private List<String> roles;

    private String username;

    private String designation;

    private String location;

    private String yearsOfExperince;

    public UserDetails(List<String> roles, String username, String designation, String location, String yearsOfExperince) {
        this.roles = roles;
        this.username = username;
        this.designation = designation;
        this.location = location;
        this.yearsOfExperince = yearsOfExperince;
    }

    public UserDetails() {
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getYearsOfExperince() {
        return yearsOfExperince;
    }

    public void setYearsOfExperince(String yearsOfExperince) {
        this.yearsOfExperince = yearsOfExperince;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
