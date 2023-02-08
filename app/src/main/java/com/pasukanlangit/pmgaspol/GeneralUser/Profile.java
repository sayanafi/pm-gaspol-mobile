package com.pasukanlangit.pmgaspol.GeneralUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pasukanlangit.pmgaspol.Config.ApiInterface;
import com.pasukanlangit.pmgaspol.Login;
import com.pasukanlangit.pmgaspol.R;
import com.pasukanlangit.pmgaspol.SuperUser.Dashboard_SU;
import com.pasukanlangit.pmgaspol.SuperUser.Members_SU;
import com.pasukanlangit.pmgaspol.SuperUser.Model.ProfileModel;
import com.pasukanlangit.pmgaspol.SuperUser.Model.UserModel;
import com.pasukanlangit.pmgaspol.SuperUser.Teams_SU;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {

    private String mNama, accesstoken, mId;
    private EditText mNamaUser;
    private TextView mLogout, mDisplayNama, displaynama;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SweetAlertDialog mProgress;
    private Button mUpdateProfil;

    EditText namauser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_profile);

        mProgress = new SweetAlertDialog(Profile.this, SweetAlertDialog.PROGRESS_TYPE);
        mNamaUser = findViewById(R.id.updateNamaField);
        mUpdateProfil = findViewById(R.id.btnUpdateProfile);

        SharedPreferences mSettings = getSharedPreferences("akun", Context.MODE_PRIVATE);
        mNama = mSettings.getString("nama",null);
        accesstoken = mSettings.getString("accesstoken", null);

        mProgress.setTitle("Loading");
        mId = getIntent().getExtras().getString("id");
        getData();

        mUpdateProfil.setOnClickListener(view -> {
            if (mNamaUser.getText().length() == 0 || mNamaUser.getText().length() == 0){
                alert("Required Field",SweetAlertDialog.WARNING_TYPE);
                return;
            }
            mProgress.show();
            update();
        });

        swipeRefreshLayout = findViewById(R.id.refreshProfile);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getData();
            swipeRefreshLayout.setRefreshing(false);
        });

        namauser = findViewById(R.id.updateNamaField);
        namauser.setText(mNama);

        displaynama = findViewById(R.id.lb_user_name);
        displaynama.setText(mNama);

        mLogout = findViewById(R.id.lbl_logout);
        mLogout.setOnClickListener(view -> {

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure you want to logout?")
                    .setConfirmText("Logout")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            SharedPreferences mSettingss = getSharedPreferences("akun", Context.MODE_PRIVATE);
                            mSettingss.edit().clear().apply();
                            startActivity(new Intent(Profile.this, Login.class));
                        }
                    })
                    .show();
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.nav_view);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), Dashboard_SU.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_listteam:
                        startActivity(new Intent(getApplicationContext(), Teams_SU.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_listmember:
                        startActivity(new Intent(getApplicationContext(), Members_SU.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void update() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder().header("Authorization", "Bearer " + accesstoken);
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }).build();
        Retrofit builder1 = new Retrofit.Builder().baseUrl("https://pmgaspol.my.id/").addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        builder1.create(ApiInterface.class).UpdateUserById(mId, new ProfileModel(namauser.getText().toString())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 201) {
                    mProgress.dismissWithAnimation();
                    SharedPreferences.Editor editor = getSharedPreferences("akun", MODE_PRIVATE).edit();
                    editor.putString("nama",namauser.getText().toString());
                    editor.apply();
                    alert("Update Success", SweetAlertDialog.SUCCESS_TYPE);
                } else {
                    mProgress.dismissWithAnimation();
                    alert("Update Failed", SweetAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mProgress.dismissWithAnimation();
                alert("Failed Get Data", SweetAlertDialog.ERROR_TYPE);
            }
        });
    }

    private void getData() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder().header("Authorization", "Bearer " + accesstoken);
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }).build();
        Retrofit builder1 = new Retrofit.Builder().baseUrl("https://pmgaspol.my.id/").addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        builder1.create(ApiInterface.class).getUserById(mId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        String responseString = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseString);
                        String nama = jsonObject.getString("nama");
                        mNamaUser.setText(nama);
                        displaynama.setText(nama);
                        mProgress.dismiss();
                    } catch (JSONException | IOException json) {
                        mProgress.dismissWithAnimation();
                        alert("Gagal Mengambil Data", SweetAlertDialog.ERROR_TYPE);
                    }
                } else {
                    mProgress.dismiss();
                    alert("Menu Tidak Ada", SweetAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mProgress.dismissWithAnimation();
                alert("Gagal Mengambil Data", SweetAlertDialog.ERROR_TYPE);
            }
        });
    }

    private void alert(String message, int sweetAlertDialog) {
        SweetAlertDialog sweetAlertDialog1 = new SweetAlertDialog(this, sweetAlertDialog);
        sweetAlertDialog1.setTitle("Status");
        sweetAlertDialog1.setContentText(message);
        sweetAlertDialog1.setConfirmText("OK");
        sweetAlertDialog1.setCanceledOnTouchOutside(true);
        sweetAlertDialog1.setCancelable(true);
        sweetAlertDialog1.setOnCancelListener(DialogInterface::dismiss);
        sweetAlertDialog1.show();
    }
}