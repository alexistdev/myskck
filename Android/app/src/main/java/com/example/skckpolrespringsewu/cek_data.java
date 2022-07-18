package com.example.skckpolrespringsewu;

import static java.lang.String.valueOf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.StatusLine;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ClientProtocolException;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class cek_data extends AppCompatActivity {
    public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    ArrayList<HashMap<String, String>> MyArrList;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    EditText strKeySearch, Tangal_awal, Tangal_akhir;
    ListView lisView1;
    Button btnSearch;
    //    private EditText btDatePicker;
    protected int[] values;
    private Double[] value;

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                Tangal_awal.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showDateDialogi() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                Tangal_akhir.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    TextView COUNT, MOUNT, tid, idhistory, status, tanggal, kimap, namaproduk, type, jumlahawal, jumlahmasuk, jumlahkeluar, jumlahhasil, nopolisi, nodoch, nocontainer, keterangan, namaperusahaan ;
    String nik, nama, kecamatan, kebutuhan,Tanggal;


    @RequiresApi(api = Build.VERSION_CODES.ECLAIR_0_1)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cek_data);

        lisView1 = (ListView) findViewById(R.id.ceklistview);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Tangal_akhir = (EditText) findViewById(R.id.tanggal_akhir);
        Tangal_awal = (EditText) findViewById(R.id.tanggal_awal);
        COUNT = (TextView) findViewById(R.id.jumlahdata);
        MOUNT = (TextView) findViewById(R.id.jumlahquantitas);

        Tangal_awal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        Tangal_akhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogi();
            }
        });
        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick (AdapterView < ? > adapterView, View view,int i, long l){
                Intent intent = new Intent(cek_data.this, input_data.class);
                //  Subject ListViewClickData = (Subject)parent.getItemAtPosition(position);
                // Toast.makeText(Cari_produk.this, ListViewClickData.getSubName(), Toast.LENGTH_SHORT).show();

                intent.putExtra("nik", nik);

                intent.putExtra("nama", nama);
                intent.putExtra("kecamatan", kecamatan);
                intent.putExtra("kebutuhan", kebutuhan);
                intent.putExtra("tanggal", Tanggal);
                startActivity(intent);

            }
        });


        // btnSearch
        btnSearch = (Button) findViewById(R.id.Cek);
        //btnSearch.setBackgroundColor(Color.TRANSPARENT);
        // Perform action on click
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Download JSON File
                new DownloadSearchFileAsync().execute();

            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id ) {

        switch (id) {
            case DIALOG_DOWNLOAD_JSON_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Loading.....");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    public class DownloadSearchFileAsync extends AsyncTask<String, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();

            showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);

        }

        @Override
        protected Void doInBackground(String... arg) {
            // TODO Auto-generated method stub

            // keySearch
            strKeySearch = (EditText)findViewById(R.id.txtKeySearch);
            Tangal_awal = (EditText) findViewById(R.id.tanggal_awal);
            Tangal_akhir = (EditText) findViewById(R.id.tanggal_akhir);


            // Disbled Keyboard auto focus
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(strKeySearch.getWindowToken(), 0);
            InputMethodManager imma = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imma.hideSoftInputFromWindow(Tangal_awal.getWindowToken(), 0);
            InputMethodManager immk = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            immk.hideSoftInputFromWindow(Tangal_akhir.getWindowToken(), 0);

            /**
             * [
             * {"MemberID":"1","Username":"weerachai","Password":"weerachai@1","Name":"Weerachai Nukitram","Tel":"0819876107","Email":"weerachai@thaicreate.com"},
             * {"MemberID":"2","Username":"adisorn","Password":"adisorn@2","Name":"Adisorn Bunsong","Tel":"021978032","Email":"adisorn@thaicreate.com"},
             * {"MemberID":"3","Username":"surachai","Password":"surachai@3","Name":"Surachai Sirisart","Tel":"0876543210","Email":"surachai@thaicreate.com"}
             * ]
             */

            String url = "http:192.168.43.49/skck/tampil.php";

            // Paste Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("txtKeyword", strKeySearch.getText().toString()));
            params.add(new BasicNameValuePair("tanggal_awal", Tangal_awal.getText().toString()));
            params.add(new BasicNameValuePair("tanggal_akhir", Tangal_akhir.getText().toString()));

            try {
                JSONArray data = new JSONArray(getJSONUrl(url,params));


                MyArrList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map;

                for(int i = 0; i < data.length(); i++){
                    JSONObject c = data.getJSONObject(i);

                    map = new HashMap<String, String>();
                    map.put("nik", c.getString("nik"));
                    map.put("nama", c.getString("nama"));
                    map.put("kecamatan", c.getString("kecamatan"));
                    map.put("tanggalh", c.getString("tanggalh"));
                    map.put("kebutuhan", c.getString("kebutuhan"));

                    MyArrList.add(map);
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            ShowSearchData(); // When Finish Show Content
            dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
            removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }

    }

    // Show Search Data
    public void ShowSearchData()
    {
        // listView1


        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(cek_data.this, MyArrList, R.layout.input_data,
                new String[] {
                        "nik",
                        "nama",
                        "kecamatan",
                        "kebutuhan",}, new int[] {
        });
        lisView1.setAdapter(sAdap);
        value = new Double[MyArrList.size()];
        values = new int[MyArrList.size()];
        for (int i = 0 ; i < MyArrList.size(); i++){

            value [i] = Double.valueOf(MyArrList.get(i).get("id_history"));
            values[i] = (int) Double.parseDouble(valueOf(value[i]));
        }
        long utrata = 0;
        long ruata = 0;
        for (int i = 0 ;i < MyArrList.size();i++){
            utrata += values[i];
            COUNT.setText(""+MyArrList.size());

        }

        MOUNT.setText(""+utrata+"");

    }

    // Get JSON code from URL
    public String getJSONUrl(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cek_data, menu);
        return true;

        ////////
}
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////