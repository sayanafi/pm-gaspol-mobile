package com.pasukanlangit.pmgaspol.Config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiLogin {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public ApiLogin(String email, String password) {
        this.email = email;
        this.password = password;
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
