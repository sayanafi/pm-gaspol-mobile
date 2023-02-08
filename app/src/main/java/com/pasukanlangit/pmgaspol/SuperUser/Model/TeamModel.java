package com.pasukanlangit.pmgaspol.SuperUser.Model;

import com.google.gson.annotations.SerializedName;

public class TeamModel {
    @SerializedName("id")
    private String id;
    @SerializedName("team")
    private String team;
    @SerializedName("deskripsi_team")
    private String deskripsi_team;

    public TeamModel(String id, String team, String deskripsi_team) {
        this.id = id;
        this.team = team;
        this.deskripsi_team = deskripsi_team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getDeskripsi_team() {
        return deskripsi_team;
    }

    public void setDeskripsi_team(String deskripsi_team) {
        this.deskripsi_team = deskripsi_team;
    }

}
