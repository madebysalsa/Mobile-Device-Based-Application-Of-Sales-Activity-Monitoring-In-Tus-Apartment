package com.example.apartemenurbansky;

import com.google.gson.annotations.SerializedName;

public class DataAbsenPulang {
    @SerializedName("nama")
    private String nama_absen_pulang;
    @SerializedName("lokasi")
    private String lokasi_absen_pulang;
    @SerializedName("tanggal")
    private String waktu;
    @SerializedName("foto")
    private String foto;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public String getNama_absen_pulang() {
        return nama_absen_pulang;
    }

    public void setNama_absen_pulang(String nama_absen_pulang) {
        this.nama_absen_pulang = nama_absen_pulang;
    }

    public String getLokasi_absen_pulang() {
        return lokasi_absen_pulang;
    }

    public void setLokasi_absen_pulang(String lokasi_absen_pulang) {
        this.lokasi_absen_pulang = lokasi_absen_pulang;
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
