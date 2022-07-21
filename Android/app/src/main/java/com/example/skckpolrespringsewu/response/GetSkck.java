package com.example.skckpolrespringsewu.response;

import com.example.skckpolrespringsewu.model.SkckModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSkck {
    @SerializedName("message")
    final private String message;

    @SerializedName("status")
    final private String status;

    @SerializedName("jumlah")
    final private String jumlah;

    @SerializedName("result")
    List<SkckModel> listSKCK;

    public GetSkck(String message, String status, String jumlah, List<SkckModel> listSKCK) {
        this.message = message;
        this.status = status;
        this.jumlah = jumlah;
        this.listSKCK = listSKCK;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getJumlah() {
        return jumlah;
    }

    public List<SkckModel> getListSKCK() {
        return listSKCK;
    }
}
