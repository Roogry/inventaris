package com.ukk.latihan1.activity.peminjaman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.ukk.latihan1.R;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListPeminjam;
import com.ukk.latihan1.modul.response.ResponsePeminjam;
import com.ukk.latihan1.utils.KeyVal;
import com.ukk.latihan1.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinjamActivity extends AppCompatActivity {

    EditText edtJumlah;
    TextView txtNama;
    Button btnPinjam;
    MaterialSpinner spPegawai;

    String id, nama, idPegawai;
    List<ListPeminjam> listPegawai;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjam);
        initLayout();

        btnPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jumlah = edtJumlah.getText().toString();
                if (Integer.parseInt(jumlah) > 0) {
                    pinjam(jumlah);
                } else {
                    Toast.makeText(PinjamActivity.this, "Masukkan jumlahnya minimal 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spPegawai.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                idPegawai = listPegawai.get(position).getIdPeminjam();
            }
        });
    }

    private void pinjam(String jumlah) {
        Call<String> call = userService.pinjam(id, jumlah, idPegawai);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(PinjamActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void initLayout() {
        edtJumlah = findViewById(R.id.edtJumlah);
        txtNama = findViewById(R.id.txtNama);
        btnPinjam = findViewById(R.id.btnPinjam);
        spPegawai = findViewById(R.id.spPegawai);

        id = getIntent().getStringExtra(KeyVal.id);
        nama = getIntent().getStringExtra(KeyVal.nama);
        txtNama.setText(nama);

        userService = APIUtils.getUserService();

        if (SharedPref.getPref(this, KeyVal.idLevel).equals("3")) {
            idPegawai = SharedPref.getPref(this, KeyVal.id);
            spPegawai.setVisibility(View.GONE);
        } else {
            getPegawai();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pinjam inventaris");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void getPegawai() {
        Call<ResponsePeminjam> call = userService.getPeminjam();
        call.enqueue(new Callback<ResponsePeminjam>() {
            @Override
            public void onResponse(Call<ResponsePeminjam> call, Response<ResponsePeminjam> response) {
                listPegawai = response.body().getListItem();
                List<String> item = new ArrayList<>();
                for (int i = 0; i < listPegawai.size(); i++) {
                    item.add(listPegawai.get(i).getNamaPeminjam());
                }
                spPegawai.setItems(item);
                idPegawai = "1";
            }

            @Override
            public void onFailure(Call<ResponsePeminjam> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
