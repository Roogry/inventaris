package com.ukk.latihan1.activity.ruang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ukk.latihan1.R;
import com.ukk.latihan1.activity.ruang.SetRuangActivity;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetRuangActivity extends AppCompatActivity {

    EditText edtNama, edtKode, edtKeterangan;
    Button btnTambah;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ruang);
        initLayut();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRuang(edtNama.getText().toString(),
                        edtKode.getText().toString(),
                        edtKeterangan.getText().toString());
            }
        });
    }

    private void setRuang(String nama, String kode, String keterangan) {
        if(!nama.isEmpty() && !kode.isEmpty()) {
            Call<String> call = userService.setRuang(nama,kode,keterangan);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(SetRuangActivity.this, response.body(), Toast.LENGTH_SHORT).show();
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
        edtKode = findViewById(R.id.edtKode);
        edtKeterangan = findViewById(R.id.edtKeterangan);
        btnTambah = findViewById(R.id.btnTambah);

        userService = APIUtils.getUserService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Daftarkan Ruang");
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
