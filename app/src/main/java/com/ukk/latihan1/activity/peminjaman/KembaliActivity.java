package com.ukk.latihan1.activity.peminjaman;

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

public class KembaliActivity extends AppCompatActivity {
    EditText edtKode;
    Button btnKembali;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kembali);
        initLayout();

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kode = edtKode.getText().toString();
                if(kode.length() == 10){
                    kembali(kode);
                }else {
                    Toast.makeText(KembaliActivity.this, "Kode tidak Valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void kembali(String kode) {
        Call<String> call = userService.kembali(kode);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(KembaliActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void initLayout() {
        edtKode = findViewById(R.id.edtKode);
        btnKembali = findViewById(R.id.btnKembali);

        userService = APIUtils.getUserService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Kembalikan inventaris");
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
