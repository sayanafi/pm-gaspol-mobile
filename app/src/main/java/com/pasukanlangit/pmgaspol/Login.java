package com.pasukanlangit.pmgaspol;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.pasukanlangit.pmgaspol.Config.ApiInterface;
import com.pasukanlangit.pmgaspol.Config.ApiLogin;
import com.pasukanlangit.pmgaspol.GeneralUser.Dashboard;
import com.pasukanlangit.pmgaspol.SuperUser.Dashboard_SU;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    SweetAlertDialog sweetAlertDialog1;
    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);

        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitle("Loading");

        //Go to Register Page
        findViewById(R.id.ButtonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        //Initialize Login API
        EditText email = findViewById(R.id.editEmailLogin);
        EditText password = findViewById(R.id.editPasswordLogin);
        Button submit = findViewById(R.id.ButtonLogin);
        submit.setOnClickListener(v -> {
            if (Objects.requireNonNull(email.getText()).length() == 0){
                email.setError("Required Field!");
                return;
            }
            if (Objects.requireNonNull(password.getText()).length() == 0){
                password.setError("Required Field!");
                return;
            }
            email.setError(null);
            password.setError(null);
            sweetAlertDialog.show();
            postLogin(email.getText().toString(),password.getText().toString());

//            sweetAlertDialog.show();
//            if (email.getText() == null || password.getText() == null) {
//                return;
//            }
//            postLogin(email.getText().toString(), password.getText().toString());
        });

    }

    private void postLogin(String email, String pass) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pmgaspol.my.id/AuthApi/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface retrofitAPI = retrofit.create(ApiInterface.class);
        ApiLogin userLogin = new ApiLogin(email, pass);
        Call<ResponseBody> call = retrofitAPI.loginPost(userLogin);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String responseString = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseString);
                    String accesstoken = jsonObject.getString("token");
                    String email = jsonObject.getString("email");
                    String role = jsonObject.getString("role");
                    String nama = jsonObject.getString("nama");
                    setSession(email, role, accesstoken, nama);
                }catch (JSONException | IOException json){
                    sweetAlertDialog.dismissWithAnimation();
                    Log.e(TAG, "onFailure: ", json);
                    alert("Error throw " + json.getLocalizedMessage(), SweetAlertDialog.ERROR_TYPE);
                }
                Toast.makeText(Login.this, "Login Success (Response " + response.code() + " )", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }
    private void alert(String message, int sweetAlertDialog) {
        sweetAlertDialog1 = new SweetAlertDialog(this,sweetAlertDialog);
        sweetAlertDialog1.setTitle("Status");
        sweetAlertDialog1.setContentText(message);
        sweetAlertDialog1.setConfirmText("OK");
        sweetAlertDialog1.show();
    }

//    private void DecodeJWT(String access) {
//        Log.d(TAG, "DecodeJWT: "+access);
//        JWT jwt = new JWT(access);
//        String username= jwt.getSubject();
//        String roles = jwt.getClaim("roles").asString(); //get custom claims
//        setSession(username,roles,access);
//    }

    private void setSession(String email, String roles,String accestoken, String nama) {
        SharedPreferences.Editor editor = getSharedPreferences("akun", MODE_PRIVATE).edit();
        editor.putString("accesstoken",accestoken);
        editor.putString("email",email);
        editor.putString("nama",nama);
        editor.putString("roles",roles);
        editor.apply();
        sweetAlertDialog.dismissWithAnimation();
        setview(roles);
    }

    private void setview(String roles) {
        Log.d(TAG, "role: "+roles);
        if (roles.contains("1")){
            startActivity(new Intent(this, Dashboard_SU.class));
            finish();
        }
        if (roles.contains("2")){
            startActivity(new Intent(this, Dashboard.class));
            finish();
        }
        if (roles.contains("3")){
            startActivity(new Intent(this, Dashboard.class));
            finish();
        }
    }
}