package com.pasukanlangit.pmgaspol.Config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiCreateTeam {
    @SerializedName("team")
    @Expose
    private String team;

    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    public ApiCreateTeam(String team, String deskripsi) {
        this.team = team;
        this.deskripsi = deskripsi;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
