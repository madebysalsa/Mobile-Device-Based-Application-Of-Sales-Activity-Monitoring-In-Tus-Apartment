package com.example.apartemenurbansky;

import com.google.gson.annotations.SerializedName;

public class DataAbsenDatang {
    @SerializedName("nama")
    private String nama_absen_datang;
    @SerializedName("lokasi")
    private String lokasi_absen_datang;
    @SerializedName("tanggal")
    private String waktu;
    @SerializedName("foto")
    private String foto;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public String getNama_absen_datang() {
        return nama_absen_datang;
    }

    public void setNama_absen_datang(String nama_absen_datang) {
        this.nama_absen_datang = nama_absen_datang;
    }

    public String getLokasi_absen_datang() {
        return lokasi_absen_datang;
    }

    public void setLokasi_absen_datang(String lokasi_absen_datang) {
        this.lokasi_absen_datang = lokasi_absen_datang;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
