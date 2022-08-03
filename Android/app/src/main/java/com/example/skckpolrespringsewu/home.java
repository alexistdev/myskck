package com.example.skckpolrespringsewu;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button button1 =(Button)findViewById(R.id.button);
        Button button2 =(Button)findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //tombol btn menuju list data
                Intent inte = new Intent(home.this, input_data.class);
                startActivity(inte);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //tombol btn2 menuju input data
                Intent inte = new Intent(home.this, cek_data.class);
                startActivity(inte);
            }
        });
    }
}