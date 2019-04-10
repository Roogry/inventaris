package com.ukk.latihan1.activity.peminjam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.ukk.latihan1.R;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.utils.KeyVal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PutPeminjamActivity extends AppCompatActivity {
    EditText edtNama, edtUsername, edtPassword, edtAlamat;
    MaterialSpinner spStatus;
    Button btnUpdate;

    UserService userService;

    String id, nama, username, password, status, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_peminjam);
        initLayout();

        id = getIntent().getStringExtra(KeyVal.id);
        nama = getIntent().getStringExtra(KeyVal.nama);
        username = getIntent().getStringExtra(KeyVal.username);
        password = getIntent().getStringExtra(KeyVal.password);
        status = getIntent().getStringExtra(KeyVal.statusPeminjam);
        alamat = getIntent().getStringExtra(KeyVal.alamat);

        setLayout();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPeminjam(id,
                        edtNama.getText().toString(),
                        edtUsername.getText().toString(),
                        edtPassword.getText().toString(),
                        edtAlamat.getText().toString(),
                        String.valueOf(spStatus.getSelectedIndex() + 1));
            }
        });
    }

    private void setLayout() {
        edtNama.setText(nama);
        edtUsername.setText(username);
        edtPassword.setText(password);
        edtAlamat.setText(alamat);

        int selected = Integer.parseInt(status) - 1;
        spStatus.setSelectedIndex(selected);
    }

    private void setPeminjam(String id, String nama, String username, String password, String alamat, String status) {
        if(!nama.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
            Call<String> call = userService.putPeminjam(id, nama, username, password, alamat, status);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(PutPeminjamActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }else{
            Toast.makeText(this, "Tolong Lengkapi kolom", Toast.LENGTH_SHORT).show();
        }
    }

    private void initLayout() {
        edtNama = findViewById(R.id.edtNama);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtAlamat = findViewById(R.id.edtAlamat);
        btnUpdate = findViewById(R.id.btnUpdate);
        spStatus = findViewById(R.id.spStatus);

        spStatus.setItems(getResources().getStringArray(R.array.status_peminjam));

        userService = APIUtils.getUserService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Daftarkan Peminjam");
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
