package com.example.apartemenurbansky;

import com.google.gson.annotations.SerializedName;

public class DataPameran {
    @SerializedName("id")
    private int id;
    @SerializedName("jenis_event")
    private String jenis_event;
    @SerializedName("name")
    private String name_pameran;
    @SerializedName("no_tlp")
    private String no_tlp;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("status")
    private String status;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJenis_event() {
        return jenis_event;
    }

    public void setJenis_event(String jenis_event) {
        this.jenis_event = jenis_event;
    }

    public String getName_pameran() {
        return name_pameran;
    }

    public void setName_pameran(String name_pameran) {
        this.name_pameran = name_pameran;
    }

    public String getNo_tlp() {
        return no_tlp;
    }

    public void setNo_tlp(String no_tlp) {
        this.no_tlp = no_tlp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
