package com.sj.config.security.filter.dto;

/**
 * Created by yangrd on 2017/12/9
 **/
public class UserLoginDTO {

    private String username;

    private String password;

    private Boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public UserLoginDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public UserLoginDTO setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
        return this;
    }
}
