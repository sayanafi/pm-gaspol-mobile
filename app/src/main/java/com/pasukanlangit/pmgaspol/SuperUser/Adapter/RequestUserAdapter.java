package com.pasukanlangit.pmgaspol.SuperUser.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.pasukanlangit.pmgaspol.Config.ApiInterface;
import com.pasukanlangit.pmgaspol.R;
import com.pasukanlangit.pmgaspol.SuperUser.Model.RequestUserModel;
import java.util.List;
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

public class RequestUserAdapter extends BaseAdapter {

    private final Context context;

    final private SharedPreferences mSettings;
    private final List<RequestUserModel> items;

    SweetAlertDialog sweetAlertDialog1;

    public RequestUserAdapter(Context context, List<RequestUserModel> items) {
        this.context = context;
        this.items = items;
        mSettings = context.getSharedPreferences("akun", Context.MODE_PRIVATE);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public RequestUserModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_request, parent, false);
        }
        RequestUserModel menuModel = getItem(position);
        TextView namauser = convertView.findViewById(R.id.nama_user);
        namauser.setText(menuModel.getNama());
        TextView emailuser = convertView.findViewById(R.id.email_user);
        emailuser.setText(menuModel.getEmail());
        Button approve = convertView.findViewById(R.id.approveuser);
        approve.setOnClickListener(v -> {
            String accesstoken = mSettings.getString("accesstoken", null);
            String mId = menuModel.getId();
            approve(mId,accesstoken);
        });
        return convertView;
    }

    private void approve(String id,String accesstoken) {
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
        builder1.create(ApiInterface.class).ApproveUserById(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Blok", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(context, "Blok 2", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
