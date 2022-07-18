package com.example.skckpolrespringsewu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.material.button.MaterialButton;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    //correct
                    Toast.makeText(login.this,"CIE PASWORD NYA BENER :)",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this,home.class);
                    startActivity(intent);

                }else
                    //incorrect
                    Toast.makeText(login.this,"HAYOOOO KAMU SIAPA?!",Toast.LENGTH_SHORT).show();
            }
        });


    }
}


