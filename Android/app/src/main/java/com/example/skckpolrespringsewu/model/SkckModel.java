package com.example.skckpolrespringsewu.model;

import com.google.gson.annotations.SerializedName;

public class SkckModel {
    @SerializedName("id")
    private final String idSKCK;

    @SerializedName("nik")
    private final String nik;

    @SerializedName("nama")
    private final String nama;

    @SerializedName("kecamatan")
    private final String kecamatan;

    @SerializedName("status")
    private final String status;

    @SerializedName("jk")
    private final String jk;

    @SerializedName("created_at")
    private final String tanggal;

    public SkckModel(String idSKCK, String nik, String nama, String kecamatan, String status, String jk, String tanggal) {
        this.idSKCK = idSKCK;
        this.nik = nik;
        this.nama = nama;
        this.kecamatan = kecamatan;
        this.status = status;
        this.jk = jk;
        this.tanggal = tanggal;
    }

    public String getIdSKCK() {
        return idSKCK;
    }

    public String getNik() {
        return nik;
    }

    public String getNama() {
        return nama;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getStatus() {
        return status;
    }

    public String getJk() {
        return jk;
    }

    public String getTanggal() {
        return tanggal;
    }
}
