package com.pasukanlangit.pmgaspol;

public class ListData {
    private String id;
    private String id_project;
    private String nama_task;
    private String deskripsi_task;
    private String tanggal_task;
    private String batas_task;
    private String status_task;


    public ListData(String id, String id_project, String nama_task,String deskripsi_task, String tanggal_task, String batas_task, String status_task) {
        this.id = id;
        this.id_project = id_project;
        this.nama_task = nama_task;
        this.deskripsi_task = deskripsi_task;
        this.tanggal_task  = tanggal_task;
        this.batas_task = batas_task;
        this.status_task = status_task;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_project() {
        return id_project;
    }

    public void setId_project(String id_project) {
        this.id_project = id_project;
    }

    public String getNama_task() {
        return nama_task;
    }

    public void setNama_task(String nama_task) {
        this.nama_task = nama_task;
    }

    public String getDeskripsi_task() {
        return deskripsi_task;
    }

    public void setDeskripsi_task(String deskripsi_task) {
        this.deskripsi_task = deskripsi_task;
    }

    public String getTanggal_task() {
        return tanggal_task;
    }

    public void setTanggal_task(String tanggal_task) {
        this.tanggal_task = tanggal_task;
    }

    public String getBatas_task() {
        return batas_task;
    }

    public void setBatas_task(String batas_task) {
        this.batas_task = batas_task;
    }

    public String getStatus_task() {
        return status_task;
    }

    public void setStatus_task(String status_task) {
        this.status_task = status_task;
    }

}

