package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;


@Entity
public class Users {

    @Id
    @Email
    public String username;

    @JsonIgnore
    public String password;

    @Column(columnDefinition = "boolean default true")
    public boolean enabled;

    @Column(columnDefinition = "boolean default true")
    public boolean accountNonExpired;

    @Column(columnDefinition = "boolean default true")
    public boolean accountNonLocked;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "users")
    @JsonIgnore
    private Authorities authorities;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "users")
    @JsonIgnore
    private UserDetails details;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return enabled;
    }

    public void setEnable(boolean enabled) {
        this.enabled = enabled;
    }


    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Authorities getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Authorities authorities) {
        this.authorities = authorities;
    }


    public UserDetails getDetails() {
        return details;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Users [username=" + username + ", password=" + password + ", enabled=" + enabled + ", authorities="
                + authorities + "]";
    }


}
