package com.example.apartemenurbansky;

import com.google.gson.annotations.SerializedName;

public class DataSales {
    @SerializedName("nama_sales")
    private String nama_sales;
    @SerializedName("jabatan")
    private String jabatan;
    @SerializedName("jenis_kel")
    private String jenis_kel;
    @SerializedName("jenis_kegiatan")
    private String jenis_kegiatan;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public String getNama_sales() {
        return nama_sales;
    }

    public void setNama_sales(String nama_sales) {
        this.nama_sales = nama_sales;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getJenis_kel() {
        return jenis_kel;
    }

    public void setJenis_kel(String jenis_kel) {
        this.jenis_kel = jenis_kel;
    }

    public String getJenis_kegiatan() {
        return jenis_kegiatan;
    }

    public void setJenis_kegiatan(String jenis_kegiatan) {
        this.jenis_kegiatan = jenis_kegiatan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
