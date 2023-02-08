package com.pasukanlangit.pmgaspol.SuperUser.Model;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("id")
    private String id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("posisi")
    private String posisi_id;

    public UserModel(String id) {
        this.id = id;
        this.nama = nama;
        this.posisi_id = posisi_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPosisi_id() {
        return posisi_id;
    }

    public void setPosisi_id(String posisi_id) {
        this.posisi_id = posisi_id;
    }
}
