package com.pasukanlangit.pmgaspol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.apache.http.entity.mime.content.StringBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    long totalSize = 0;
    String user = "";
    String email = "";
    String password = "";

    EditText txtuser;
    EditText txtemail;
    EditText txtpass;

    ProgressDialog progDi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        txtuser = findViewById(R.id.editNameRegister);
        txtemail = findViewById(R.id.editEmailRegister);
        txtpass = findViewById(R.id.editPasswordRegiste);

        findViewById(R.id.backbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });


        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user = txtuser.getText().toString();
                email = txtemail.getText().toString();
                password = Login.md5(txtpass.getText().toString());

                if (isEmailValid(email)) {
                    if (txtpass.length() < 8){
                        Snackbar.make(findViewById(android.R.id.content), "password minimal 8 karakter", Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        //Snackbar.make(findViewById(android.R.id.content), "sukses", Snackbar.LENGTH_LONG).show();
                        //new UploadFileToServer().execute();
                        if (user.isEmpty()){
                            Snackbar.make(findViewById(android.R.id.content), "nama kosong cuy", Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            new UploadFileToServer().execute();}
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Format email salah!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            progDi = new ProgressDialog(Register.this);
            progDi.setMessage("Mendaftar...");
            progDi.setIndeterminate(false);
            progDi.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDi.setCancelable(false);
            progDi.setCanceledOnTouchOutside(false);
            progDi.show();
            super.onPreExecute();
        }
        //kumagumi

        @Override
        protected String doInBackground(Void... params) {
            String data = null;
            try {
                data = uploadFile();
            } catch (Exception e) {
                e.printStackTrace();
                data = "FAILED";
            }
            return data;
        }

        private String uploadFile() throws Exception {
            URL url = new URL("https://pmgaspol.my.id/mobile/register.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });
            entity.addPart("user", new StringBody(user + ""));
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
                if (result.equalsIgnoreCase("OK")) {
                    Toast.makeText(Register.this, "Silahkan masuk dengan Nama Pengguna dan Password anda", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, Login.class));
                } else {
                    Snackbar.make(findViewById(android.R.id.content), result, Snackbar.LENGTH_LONG).show();
                }
            }
            super.onPostExecute(result);
        }
    }
}