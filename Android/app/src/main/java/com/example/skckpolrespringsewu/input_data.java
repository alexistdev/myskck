package com.example.skckpolrespringsewu;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.skckpolrespringsewu.api.APIService;
import com.example.skckpolrespringsewu.model.SkckModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class input_data extends AppCompatActivity {
    private ProgressDialog pDialog;
    private EditText mNik,mNama;
    private Spinner mKecamatan, mStatus, mJk;
    private Button mSimpan;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_data);
        Toolbar toolbar = findViewById(R.id.tbtoolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tambah Data");
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initData();
        mSimpan.setOnClickListener(view -> prosesSimpan());
    }
    private void prosesSimpan() {

        tampilLoading();
        String nik = mNik.getText().toString();
        String nama = mNama.getText().toString();
        String kecamatan = mKecamatan.getSelectedItem().toString();
        String status = mStatus.getSelectedItem().toString();
        String jk = mJk.getSelectedItem().toString();
        if(nik.length() == 0 || nama.length() == 0 || kecamatan.length() == 0 || status.length() == 0 || jk.length() == 0 ){
            SembunyiLoading();
            pesan("Semua kolom harus diisi!");
        } else {
            try{
                Call<SkckModel> call= APIService.Factory.create(getApplicationContext()).tambahData(nik,nama,kecamatan,status,jk);
                call.enqueue(new Callback<SkckModel>() {
                    @EverythingIsNonNull
                    @Override
                    public void onResponse(Call<SkckModel> call, Response<SkckModel> response) {
                        if(response.isSuccessful()) {
                            SembunyiLoading();
                            pesan("Data SKCK Berhasil ditambahkan!");
                        }
                    }
                    @EverythingIsNonNull
                    @Override
                    public void onFailure(Call<SkckModel> call, Throwable t) {
                        SembunyiLoading();
                        pesan(t.getMessage());
                    }
                });
            }catch (Exception e){
                SembunyiLoading();
                e.printStackTrace();
                pesan(e.getMessage());
            }
        }

    }
    private void initData(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");
        mNik = findViewById(R.id.inputnik);
        mNama = findViewById(R.id.inputnama);
        mKecamatan = findViewById(R.id.spn_kecamatan);
        mStatus = findViewById(R.id.spn_status);
        mJk = findViewById(R.id.spn_jk);
        mSimpan = findViewById(R.id.buttoninput);
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

}

