package com.example.skckpolrespringsewu;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skckpolrespringsewu.adapter.DatePickerFragment;
import com.example.skckpolrespringsewu.adapter.Skckadapter;
import com.example.skckpolrespringsewu.api.APIService;
import com.example.skckpolrespringsewu.model.SkckModel;
import com.example.skckpolrespringsewu.response.GetSkck;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class cek_data extends AppCompatActivity implements View.OnClickListener,Skckadapter.ClickListener {
    private ProgressDialog pDialog;
    private Skckadapter skckadapter;
    private List<SkckModel> daftarskck;
    private RecyclerView gridDiary;
    private Button mCari;
    private EditText edCari,edMulai,edAkhir;
    private TextView jmlData;
    private Skckadapter.ClickListener clickListener;
//    private int mYear, mMonth, mDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cek_data);
        clickListener=this;
        initData();
        setupRecyclerView();

        edMulai.setOnClickListener(this);
        edAkhir.setOnClickListener(this);

        mCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = edCari.getText().toString();
                String tanggalMulai = edMulai.getText().toString();
                String tanggalAkhir = edAkhir.getText().toString();

                setData(search,tanggalMulai,tanggalAkhir);
            }
        });
    }
    @Override
    public void dataItem(String idSKCK, String msg) {
        hapus(idSKCK);
    }

    private void hapus(String idSKCK){
        try{
            tampilLoading();
            Call<SkckModel> call=APIService.Factory.create(getApplicationContext()).hapusData(idSKCK);
            call.enqueue(new Callback<SkckModel>() {
                @Override
                public void onResponse(Call<SkckModel> call, Response<SkckModel> response) {
                    SembunyiLoading();
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            pesan("data berhasil dihapus!");
                            setupRecyclerView();
                            setData(null,null,null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<SkckModel> call, Throwable t) {
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

    @Override
    public void onClick(View v) {
        if (v == edMulai) {
            final Calendar calendar = Calendar.getInstance ();
            int mYear = calendar.get ( Calendar.YEAR );
            int mMonth = calendar.get ( Calendar.MONTH );
            int mDay = calendar.get ( Calendar.DAY_OF_MONTH );

            DatePickerDialog datePickerDialog = new DatePickerDialog ( this, new DatePickerDialog.OnDateSetListener () {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edMulai.setText ( dayOfMonth + "-" + (month + 1) + "-" + year );
                }
            }, mYear, mMonth, mDay );
            datePickerDialog.show ();
        }
        if (v == edAkhir) {
            final Calendar calendar = Calendar.getInstance ();
            int mYear = calendar.get ( Calendar.YEAR );
            int mMonth = calendar.get ( Calendar.MONTH );
            int mDay = calendar.get ( Calendar.DAY_OF_MONTH );

            //show dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog ( this, new DatePickerDialog.OnDateSetListener () {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edAkhir.setText ( dayOfMonth + "-" + (month + 1) + "-" + year );
                }
            }, mYear, mMonth, mDay );
            datePickerDialog.show ();
        }
    }

    private void setData(String search,String tanggalMulai,String tanggalAkhir){
        try{
            tampilLoading();
            Call<GetSkck> call= APIService.Factory.create(getApplicationContext()).dapatSKCK(search,tanggalMulai,tanggalAkhir);
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
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void initData(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");
        gridDiary = findViewById(R.id.rcSKCK);
        mCari = findViewById(R.id.btn_cek);
        edCari = findViewById(R.id.edx_cari);
        jmlData = findViewById(R.id.jumlahdata);
        edMulai = findViewById(R.id.tanggal_awal);
        edAkhir = findViewById(R.id.tanggal_akhir);
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
        skckadapter = new Skckadapter(new ArrayList<>(),this);
        gridDiary.setLayoutManager(linearLayoutManager);
        gridDiary.setAdapter(skckadapter);
    }


}
