package com.pasukanlangit.pmgaspol.SuperUser;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.pasukanlangit.pmgaspol.Config.ApiInterface;
import com.pasukanlangit.pmgaspol.GeneralUser.Profile;
import com.pasukanlangit.pmgaspol.R;
import com.pasukanlangit.pmgaspol.SuperUser.Adapter.UserAdapter;
import com.pasukanlangit.pmgaspol.SuperUser.Model.UserModel;

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
import retrofit2.http.Query;

public class Members_SU extends AppCompatActivity {

    String accesstoken;
    ListView listuser;
    SwipeRefreshLayout swipeRefreshLayout;

    SearchView searchmember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_member_list_su);
        listuser = findViewById(R.id.listUser);

        swipeRefreshLayout = findViewById(R.id.refreshUser);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getData();
            swipeRefreshLayout.setRefreshing(false);
        });

        // Go To Request Member Page
        // Close Create Team
        findViewById(R.id.imgnotif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Members_SU.this, ApproveMember_SU.class));
                finish();
            }
        });

        getData();
        SharedPreferences editor = getSharedPreferences("akun", MODE_PRIVATE);
        accesstoken = editor.getString("accesstoken", null);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), Dashboard_SU.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_listteam:
                        startActivity(new Intent(getApplicationContext(), Teams_SU.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_listmember:
                        startActivity(new Intent(getApplicationContext(), Members_SU.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

/*        // Find SearchView
        SearchView searchView = searchmember.findViewById(R.id.searchMember);

        // Set search view listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getUser(query);
                return false;
            };

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            };
        });*/

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
        builder1.create(ApiInterface.class).getAlluser().enqueue(new Callback<ResponseBody>() {
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
                        Type listType = new TypeToken<List<UserModel>>(){}.getType();
                        List<UserModel> menuitem = gson.fromJson(tes, listType);
                        UserAdapter adapter = new UserAdapter(getApplicationContext(), menuitem);
                        adapter.notifyDataSetChanged();
                        listuser.setAdapter(adapter);
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

    /*private void getUser(String query) {
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
        builder1.create(ApiInterface.class).searchUserById("query").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody dataResponse = response.body();
                Toast.makeText(getApplicationContext(), "User Ditemukan", Toast.LENGTH_SHORT).show();
                // Do something with the data response
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
            }
        });
    }*/


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
