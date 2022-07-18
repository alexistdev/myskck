package com.example.skckpolrespringsewu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.TextSelection;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class input_data extends AppCompatActivity implements View.OnClickListener{

    private Button btninput;
    private EditText inputnik,inputnama;

    public static final String URLINSERT = "http://192.168.43.49/skck/insert.php";

    Spinner spinerkebutuhan, spinerkecamatan, spinerjeniskelamin;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_data);




    }

    //Dibawah ini merupakan perintah untuk Menambahkan skck (CREATE)
    private void tambahskck(){

        final String nik = inputnik.getText().toString().trim();
        final String nama = inputnama.getText().toString().trim();
        final AdapterView.OnItemSelectedListener kecamatan = spinerkecamatan.getOnItemSelectedListener();
        final AdapterView.OnItemSelectedListener kebutuhan = spinerkebutuhan.getOnItemSelectedListener();


        class tambahskck extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(input_data.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(input_data.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NIK,nik);
                params.put(konfigurasi.KEY_EMP_NAMA,nama);
                params.put(konfigurasi.KEY_EMP_KECAMATAN, String.valueOf(kecamatan));
                params.put(konfigurasi.KEY_EMP_KEBUTUHAN, String.valueOf(kebutuhan));




                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        tambahskck ae = new tambahskck();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == btninput){
            tambahskck();
        }



    }
}

