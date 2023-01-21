package com.pasukanlangit.pmgaspol.Config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiRegister {
    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("email")
    @Expose
    private String email;

//    @SerializedName("phone_number")
//    @Expose
//    private String phone_number;

    @SerializedName("password")
    @Expose
    private String password;

    public ApiRegister(String nama, String email, String phone_number) {
        this.nama = nama;
        this.email = email;
//        this.phone_number = phone_number;
        this.password = password;
    }

    public String getName() {
        return nama;
    }

    public void setName(String name) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getPhone_number() {
//        return phone_number;
//    }
//
//    public void setPhone_number(String phone_number) {
//        this.phone_number = phone_number;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
