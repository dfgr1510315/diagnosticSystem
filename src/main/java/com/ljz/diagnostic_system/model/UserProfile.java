package com.ljz.diagnostic_system.model;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 用户登录参数
 */
public class UserProfile implements Serializable {
    @NotEmpty(message = "邮箱不能为空")
    private String email;
    @NotEmpty(message = "密码不能为空")
    private String password;

    private String id;

    private String username;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
