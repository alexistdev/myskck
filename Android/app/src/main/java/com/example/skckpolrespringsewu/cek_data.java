package com.example.skckpolrespringsewu;

import static java.lang.String.valueOf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skckpolrespringsewu.adapter.Skckadapter;
import com.example.skckpolrespringsewu.api.APIService;
import com.example.skckpolrespringsewu.model.SkckModel;
import com.example.skckpolrespringsewu.response.GetSkck;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class cek_data extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Skckadapter skckadapter;
    private List<SkckModel> daftarskck;
    private RecyclerView gridDiary;
    private Button mCari;
    private EditText edCari;
    private TextView jmlData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cek_data);
        initData();
        setupRecyclerView();

        mCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = edCari.getText().toString();
                setData(search);
            }
        });
    }
    private void setData(String search){
        try{
            Call<GetSkck> call= APIService.Factory.create(getApplicationContext()).dapatSKCK(search);
            call.enqueue(new Callback<GetSkck>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetSkck> call, Response<GetSkck> response) {
                    SembunyiLoading();
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            jmlData.setText(response.body().getJumlah());
                            daftarskck = response.body().getListSKCK();
                            skckadapter.replaceData(daftarskck);
                        }
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<GetSkck> call, Throwable t) {
                    SembunyiLoading();
                    pesan(t.getMessage());
                }
            });
        } catch (Exception e) {
            SembunyiLoading();
            e.printStackTrace();
            pesan(e.getMessage());
        }
    }
    private void initData(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");
        gridDiary = findViewById(R.id.rcSKCK);
        mCari = findViewById(R.id.btn_cek);
        edCari = findViewById(R.id.edx_cari);
        jmlData = findViewById(R.id.jumlahdata);
    }

    private void tampilLoading(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void SembunyiLoading(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    public void pesan(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        skckadapter = new Skckadapter(new ArrayList<>());
        gridDiary.setLayoutManager(linearLayoutManager);
        gridDiary.setAdapter(skckadapter);
    }

}
