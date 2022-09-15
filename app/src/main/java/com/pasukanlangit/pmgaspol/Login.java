package com.pasukanlangit.pmgaspol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.apache.http.entity.mime.content.StringBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    long totalSize = 0;
    String email = "";
    String password = "";
    EditText txtemail;
    EditText txtpass;
    ProgressDialog progDi;

    public static String md5(final String s){
        final String MD5 = "MD5";
        try {
            //create hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            //create hex
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xff & aMessageDigest);
                while (h.length() < 2) h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.activity_login);

        //initiate field
        txtemail = findViewById(R.id.editEmailLogin);
        txtpass = findViewById(R.id.editPasswordLogin);

        findViewById(R.id.ButtonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //filter
                email = txtemail.getText().toString();
                password = md5(txtpass.getText().toString());
                if (email.isEmpty() || password.isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Data Tidak Boleh Kosong",Toast.LENGTH_SHORT);
                }
                else
                {
                    new UploadFileToServer().execute();
                    //startActivity(new Intent(Login.this, Dashboard.class));startActivity(new Intent(Login.this, Dashboard.class));
                }
            }
        });

        findViewById(R.id.ButtonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            progDi = new ProgressDialog(Login.this);
            progDi.setMessage("Masuk...");
            progDi.setIndeterminate(false);
            progDi.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDi.setCancelable(false);
            progDi.setCanceledOnTouchOutside(false);
            progDi.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String data = null;
            try {
                data = uploadFile();
            } catch (Exception e) {
                e.printStackTrace();
                data = "FAIL";
            }
            return data;
        }

        private String uploadFile() throws Exception {
            URL url = new URL("https://pmgaspol.my.id/mobile/login.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            publishProgress(((int) ((num / (float) totalSize) * 100)));
                        }
                    });
            entity.addPart("email", new StringBody(email + ""));
            entity.addPart("password", new StringBody(password + ""));

            totalSize = entity.getContentLength();
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.addRequestProperty("Content-length", totalSize + "");
            con.addRequestProperty(entity.getContentType().getName(), entity.getContentType().getValue());

            OutputStream os = con.getOutputStream();
            entity.writeTo(con.getOutputStream());
            os.close();
            con.connect();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            progDi.dismiss();
            if (result != null) {
                Log.e("UPLOAD", result);
                if (!result.equalsIgnoreCase("FAILED")) {
                    String[] hasil = result.split("\\|");
                    SharedPreferences.Editor mEditor = getSharedPreferences("PMGASPOL", 0).edit();
                    mEditor.putString("user", hasil[0]).apply();
                    mEditor.putString("email", hasil[1]).apply();
                    //mEditor.putString("password", hasil[2]).apply();

                    Intent intent = new Intent(Login.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Pengguna atau Kata sandi salah!", Snackbar.LENGTH_LONG).show();
                }
            }
            super.onPostExecute(result);
        }
    }
}