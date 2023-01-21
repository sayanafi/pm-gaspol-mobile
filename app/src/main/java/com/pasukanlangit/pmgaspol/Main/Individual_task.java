package com.pasukanlangit.pmgaspol.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pasukanlangit.pmgaspol.HttpHandler;
import com.pasukanlangit.pmgaspol.ListAdapter;
import com.pasukanlangit.pmgaspol.ListData;
import com.pasukanlangit.pmgaspol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Individual_task extends AppCompatActivity {

    RecyclerView recyclerView;
    ListAdapter adapter;
    ArrayList<com.pasukanlangit.pmgaspol.ListData> ListData = new ArrayList<>();
    private GetTask gTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_individual_task);

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
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_intask:
                        startActivity(new Intent(getApplicationContext(),Individual_task.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_listteam:
                        startActivity(new Intent(getApplicationContext(),Team_task.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        adapter = new ListAdapter(ListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Individual_task.this));
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);

        new GetTask().execute();

        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gTask = new GetTask();
                gTask.execute();
                pullToRefresh.setRefreshing(false);
            }
        });


    }

    private class GetTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(Dashboard.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String user = getSharedPreferences("PMGASPOL", 0).getString("user", "");
            ListData = new ArrayList<>();
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://pmgaspol.my.id/mobile/readtask.php";
            String jsonStr = sh.makeServiceCall(url);

            Log.e("Donlot json", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray trips = jsonObj.getJSONArray("emparray");

                    // looping through All Contacts
                    for (int i = 0; i < trips.length(); i++) {
                        JSONObject g = trips.getJSONObject(i);

                        String id = g.getString("id");
                        String id_project = g.getString("id_project");
                        String nama_task = g.getString("nama_task");
                        String deskripsi_task = g.getString("deskripsi_task");
                        String tanggal_task = g.getString("tanggal_task");
                        String batas_task = g.getString("batas_task");
                        String status_task = g.getString("status_task");



                        ListData travel = new ListData(id,id_project,nama_task,deskripsi_task,tanggal_task,batas_task,status_task);

                        ListData.add(travel);

                        Log.e("Download json", "Json total : " + ListData.size());
                    }
                } catch (final JSONException e) {
                    Log.e("Donlot json", "Json parsing error: " + e.getMessage());
                    //Toast.makeText(Dashboard.this,"Json parsing error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }

            } else {
                Log.e("Donlot json", "Couldn't get json from server.");
                Toast.makeText(getApplicationContext(),
                        "Couldn't get json from server. Check LogCat for possible errors!",
                        Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new ListAdapter(ListData);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);
        }
    }
}