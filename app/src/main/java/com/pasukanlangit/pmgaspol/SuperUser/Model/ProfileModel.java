package com.pasukanlangit.pmgaspol.SuperUser.Model;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {
    @SerializedName("nama")
    String nama;

    public ProfileModel(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
