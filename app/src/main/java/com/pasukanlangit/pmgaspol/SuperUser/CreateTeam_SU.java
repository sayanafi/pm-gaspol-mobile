package com.pasukanlangit.pmgaspol.SuperUser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pasukanlangit.pmgaspol.Config.ApiCreateTeam;
import com.pasukanlangit.pmgaspol.Config.ApiInterface;
import com.pasukanlangit.pmgaspol.Config.ApiRegister;
import com.pasukanlangit.pmgaspol.Login;
import com.pasukanlangit.pmgaspol.R;
import com.pasukanlangit.pmgaspol.Register;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateTeam_SU extends AppCompatActivity {
    String accesstoken;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_create_team_su);

        // Close Create Team
        findViewById(R.id.imgclose_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateTeam_SU.this, Teams_SU.class));
                finish();
            }
        });
        SharedPreferences editor = getSharedPreferences("akun", MODE_PRIVATE);
        accesstoken = editor.getString("accesstoken",null);
        //Initialize Create Team API
        EditText team = findViewById(R.id.editNamaTeam);
        EditText desk = findViewById(R.id.editDeskripsiTeam);
        Button submit = findViewById(R.id.btnCreateTeam);

        submit.setOnClickListener(v -> {
            if (Objects.requireNonNull(team.getText()).length() == 0){
                team.setError("Required Field!");
                return;
            }
            post(team.getText().toString(), desk.getText().toString());
        });
    }

    //Create Team API
    private void post(String team, String desk) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder().header("Authorization", "Bearer "+accesstoken);
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https:/pmgaspol.my.id/").addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        ApiInterface retrofitAPI = retrofit.create(ApiInterface.class);
        ApiCreateTeam createTeam = new ApiCreateTeam(team, desk);
        Call<ResponseBody> call = retrofitAPI.teamPost(createTeam);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(CreateTeam_SU.this, "Team Created (Response " + response.code() + " )", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateTeam_SU.this, Teams_SU.class));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CreateTeam_SU.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}