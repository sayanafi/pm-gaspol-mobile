package com.pasukanlangit.pmgaspol.SuperUser.Model;

import com.google.gson.annotations.SerializedName;

public class ProjectModel {
    @SerializedName("nama_project")
    private String nama_project;
    @SerializedName("status_project")
    private String status;
    @SerializedName("team")
    private String nama_team;

    public ProjectModel(String nama_project, String status, String nama_team) {
        this.nama_project = nama_project;
        this.status = status;
        this.nama_team = nama_team;
    }

    public String getNama_project() {
        return nama_project;
    }

    public void setNama_project(String nama_project) {
        this.nama_project = nama_project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNama_team() {
        return nama_team;
    }

    public void setNama_team(String nama_team) {
        this.nama_team = nama_team;
    }
}