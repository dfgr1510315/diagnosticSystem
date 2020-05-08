package com.ljz.diagnostic_system.model;

import java.io.Serializable;

public class UserToken implements Serializable {
    private String id;

    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
