package com.pasukanlangit.pmgaspol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pasukanlangit.pmgaspol.Config.ApiClient;
import com.pasukanlangit.pmgaspol.Config.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);

        //Back to Login Page
        findViewById(R.id.backbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });


        EditText nama = findViewById(R.id.editNameRegister);
        EditText email = findViewById(R.id.editEmailRegister);
        EditText password = findViewById(R.id.editPasswordRegister);
        Button submit = findViewById(R.id.btnRegister);

        submit.setOnClickListener(v -> {
            if (nama.getText() == null || email.getText() == null || password.getText() == null) {
                return;
            }
            post(nama.getText().toString(), email.getText().toString(), password.getText().toString());
        });
    }

    private void post(String nama, String email, String pass) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pmgaspol.my.id/UsersApi/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface retrofitAPI = retrofit.create(ApiInterface.class);
        ApiClient userModel1 = new ApiClient(nama, email, pass);
        Call<ApiClient> call = retrofitAPI.createPost(userModel1);
        call.enqueue(new Callback<ApiClient>() {
            @Override
            public void onResponse(Call<ApiClient> call, Response<ApiClient> response) {
                Toast.makeText(Register.this, "Data added to API " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiClient> call, Throwable t) {
                Toast.makeText(Register.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}