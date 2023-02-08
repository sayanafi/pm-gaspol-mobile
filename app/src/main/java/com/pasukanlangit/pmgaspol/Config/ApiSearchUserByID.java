package com.pasukanlangit.pmgaspol.Config;

import com.google.gson.annotations.SerializedName;

public class ApiSearchUserByID {
    @SerializedName("id")
    private String id;

    @SerializedName("nama")
    private String nama;

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public ApiSearchUserByID(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
