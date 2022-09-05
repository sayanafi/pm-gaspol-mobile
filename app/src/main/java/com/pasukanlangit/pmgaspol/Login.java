package com.pasukanlangit.pmgaspol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.activity_login);

        //initiate field
        final EditText txtemail = findViewById(R.id.editEmailLogin);
        final EditText txtpassword = findViewById(R.id.editPasswordLogin);

        findViewById(R.id.ButtonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //filter
                String email = txtemail.getText().toString();
                String password = txtpassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Data Tidak Boleh Kosong",Toast.LENGTH_SHORT);
                }
                else
                {
                    startActivity(new Intent(Login.this, Dashboard.class));startActivity(new Intent(Login.this, Dashboard.class));
                }
            }
        });

        findViewById(R.id.ButtonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }


}