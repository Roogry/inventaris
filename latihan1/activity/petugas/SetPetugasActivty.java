package com.ukk.latihan1.activity.petugas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ukk.latihan1.R;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPetugasActivty extends AppCompatActivity {

    EditText edtNama, edtUsername, edtPassword;
    Button btnTambah;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_petugas);
        initLayut();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPetugas(edtNama.getText().toString(),
                        edtUsername.getText().toString(),
                        edtPassword.getText().toString());
            }
        });
    }

    private void setPetugas(String nama, String username, String password) {
        if(!nama.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
            Call<String> call = userService.setPetugas(nama,username,password);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(SetPetugasActivty.this, response.body(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }else{
            Toast.makeText(this, "Tolong Lengkapi kolom", Toast.LENGTH_SHORT).show();
        }
    }

    private void initLayut() {
        edtNama = findViewById(R.id.edtNama);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnTambah = findViewById(R.id.btnTambah);

        userService = APIUtils.getUserService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Daftarkan Petugas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
