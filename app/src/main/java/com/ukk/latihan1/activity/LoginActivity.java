package com.ukk.latihan1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ukk.latihan1.R;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListUser;
import com.ukk.latihan1.modul.response.ResponseLogin;
import com.ukk.latihan1.utils.KeyVal;
import com.ukk.latihan1.utils.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.initLayout();

        if(SharedPref.isLogined(LoginActivity.this)){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(edtUsername.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    private void login(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()) {

            Call<ResponseLogin> call = userService.login(username, password);
            call.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    ListUser user = response.body().getListItem();

                    SharedPref.setLogined(LoginActivity.this);
                    SharedPref.putPref(LoginActivity.this, KeyVal.id, user.getId());
                    SharedPref.putPref(LoginActivity.this, KeyVal.nama, user.getNama());
                    SharedPref.putPref(LoginActivity.this, KeyVal.username, user.getUsername());
                    SharedPref.putPref(LoginActivity.this, KeyVal.idLevel, user.getIdLevel());
                    SharedPref.putPref(LoginActivity.this, KeyVal.alamat, user.getAlamat());
                    SharedPref.putPref(LoginActivity.this, KeyVal.statusPeminjam, user.getStatusPeminjam());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    Log.e("error : ", t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Tolong masukkan username password", Toast.LENGTH_SHORT).show();
        }
    }

    private void initLayout() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        userService = APIUtils.getUserService();
    }
}
