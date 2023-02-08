package com.pasukanlangit.pmgaspol.SuperUser;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.pasukanlangit.pmgaspol.Config.ApiInterface;
import com.pasukanlangit.pmgaspol.GeneralUser.Profile;
import com.pasukanlangit.pmgaspol.R;
import com.pasukanlangit.pmgaspol.SuperUser.Adapter.TeamAdapter;
import com.pasukanlangit.pmgaspol.SuperUser.Model.TeamModel;
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

public class Teams_SU extends AppCompatActivity {
    String accesstoken;

    ListView listteam;
    SwipeRefreshLayout swipeRefreshLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_teams_su);
        listteam = findViewById(R.id.listTeam);

        swipeRefreshLayout = findViewById(R.id.refreshTeam);
        swipeRefreshLayout.setOnRefreshListener(()->{
            getData();
            swipeRefreshLayout.setRefreshing(false);
        });

        // Go To Create Team Page
        findViewById(R.id.btnCreateTeam).setOnClickListener(view -> startActivity(new Intent(Teams_SU.this, CreateTeam_SU.class)));

        getData();
        SharedPreferences editor = getSharedPreferences("akun", MODE_PRIVATE);
        accesstoken = editor.getString("accesstoken",null);

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
        Retrofit builder1 = new Retrofit.Builder().baseUrl("https://pmgaspol.my.id/").addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        builder1.create(ApiInterface.class).getAllTeam().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        String respon = response.body().string();
                        JSONObject jsonObject = new JSONObject(respon);
                        String tes = jsonObject.getString("team");
                        Log.d(TAG, "onResponse: "+tes);
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<TeamModel>>(){}.getType();
                        List<TeamModel> menuitem = gson.fromJson(tes, listType);
                        TeamAdapter adapter = new TeamAdapter(getApplicationContext(), menuitem);
                        adapter.notifyDataSetChanged();
                        listteam.setAdapter(adapter);
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