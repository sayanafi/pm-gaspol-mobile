package com.pasukanlangit.pmgaspol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.activity_register);

        //initiate field
        final EditText txtuser = findViewById(R.id.editNameRegister);
        final EditText txtemail = findViewById(R.id.editEmailRegister);
        final EditText txtpassword = findViewById(R.id.editPasswordRegiste);

        findViewById(R.id.backbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // filter
                String user = txtuser.getText().toString();
                String email = txtemail.getText().toString();
                String password = txtpassword.getText().toString();
                if(user.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Data Tidak Boleh Kosong",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    openDialog();
                }
            }
        });

    }

    public void openDialog(){
        register_popup regispopup = new register_popup();
        regispopup.show(getSupportFragmentManager(),"nyee");
    }

}