package com.pasukanlangit.pmgaspol.SuperUser;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.pasukanlangit.pmgaspol.Config.ApiInterface;
import com.pasukanlangit.pmgaspol.GeneralUser.Profile;
import com.pasukanlangit.pmgaspol.Login;
import com.pasukanlangit.pmgaspol.R;
import com.pasukanlangit.pmgaspol.SuperUser.Adapter.RequestUserAdapter;
import com.pasukanlangit.pmgaspol.SuperUser.Model.RequestUserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
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

public class ApproveMember_SU extends AppCompatActivity {

    String accesstoken;
    ListView listrequest;
    SwipeRefreshLayout swipeRefreshLayout;
    Button approve;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_approve_member_su);

        listrequest = findViewById(R.id.listRequest);

        swipeRefreshLayout = findViewById(R.id.refreshRequest);
        swipeRefreshLayout.setOnRefreshListener(()->{
            getData();
            swipeRefreshLayout.setRefreshing(false);
        });

        getData();
        SharedPreferences editor = getSharedPreferences("akun", MODE_PRIVATE);
        accesstoken = editor.getString("accesstoken",null);

        // Close Request Member
        findViewById(R.id.imgclose_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ApproveMember_SU.this, Members_SU.class));
                finish();
            }
        });

//        findViewById(R.id.approveuser).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int id = 1;
//                approve(id, "https://pmgaspol.my.id/");
//            }
//        });

    }



    private void getData() {
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
        Retrofit builder1 = new Retrofit.Builder().baseUrl("https:/pmgaspol.my.id/").addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        builder1.create(ApiInterface.class).getAllUserRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        String respon = response.body().string();
                        JSONObject jsonObject = new JSONObject(respon);
                        String tes = jsonObject.getString("users");
                        Log.d(TAG, "onResponse: "+tes);
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<RequestUserModel>>(){}.getType();
                        List<RequestUserModel> menuitem = gson.fromJson(tes, listType);
                        RequestUserAdapter adapter = new RequestUserAdapter(getApplicationContext(), menuitem);
                        adapter.notifyDataSetChanged();
                        listrequest.setAdapter(adapter);
                    }catch (JsonIOException | IOException | JSONException jsonException){
                        alert("Error", SweetAlertDialog.ERROR_TYPE);
                    }
                } else if (response.code() == 401) {
                    alert("Can't Access ", SweetAlertDialog.WARNING_TYPE);
                } else {
                    alert("Error " + response.code(), SweetAlertDialog.ERROR_TYPE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                alert("Error", SweetAlertDialog.ERROR_TYPE);
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