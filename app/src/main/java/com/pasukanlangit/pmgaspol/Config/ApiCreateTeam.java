package com.pasukanlangit.pmgaspol.Config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiCreateTeam {
    @SerializedName("team")
    @Expose
    private String team;

    @SerializedName("deskripsi_team")
    @Expose
    private String deskripsiteam;

    public ApiCreateTeam(String team, String deskripsiteam) {
        this.team = team;
        this.deskripsiteam = deskripsiteam;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String email) {
        this.team = team;
    }

    public String getDeskripsiteam() {
        return deskripsiteam;
    }

    public void setPassword(String team) {
        this.deskripsiteam = deskripsiteam;
    }
}
