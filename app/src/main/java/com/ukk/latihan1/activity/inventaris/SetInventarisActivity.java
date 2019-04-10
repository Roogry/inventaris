package com.ukk.latihan1.activity.inventaris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.ukk.latihan1.R;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListJenis;
import com.ukk.latihan1.modul.list.ListRuang;
import com.ukk.latihan1.modul.response.ResponseJenis;
import com.ukk.latihan1.modul.response.ResponseRuang;
import com.ukk.latihan1.utils.KeyVal;
import com.ukk.latihan1.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetInventarisActivity extends AppCompatActivity {

    EditText edtNama, edtJumlah;
    Button btnTambah;
    MaterialSpinner spJenis, spRuang, spKondisi, spKeterangan;

    UserService userService;
    List<ListJenis> listIJenis;
    List<ListRuang> listRuang;

    String idUser, keterangan = "Barang Pakai Habis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_inventaris);
        initLayut();
        getJenis();
        getRuang();

        idUser = SharedPref.getPref(this, KeyVal.id);

        spKeterangan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                keterangan = item;
                Log.e("keterangan : ", keterangan);
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInvent(
                        edtNama.getText().toString(),
                        edtJumlah.getText().toString(),
                        listIJenis.get(spJenis.getSelectedIndex()).getIdJenis(),
                        listRuang.get(spRuang.getSelectedIndex()).getIdRuang(),
                        String.valueOf(spKondisi.getSelectedIndex() + 1),
                        keterangan,
                        idUser
                );
            }
        });
    }

    private void setInvent(String nama, String jumlah, String idJenis, String idRuang, String kondisi, String keterangan, String idUser) {
        if(!nama.isEmpty() && !jumlah.isEmpty()){
            Call<String> call = userService.setInventaris(nama,kondisi,keterangan,jumlah,idJenis,idRuang,idUser);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(SetInventarisActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }else{
            Toast.makeText(this, "Tolong Lengkapi kolom", Toast.LENGTH_SHORT).show();
        }

    }

    private void getJenis() {
        Call<ResponseJenis> call = userService.getJenis();
        call.enqueue(new Callback<ResponseJenis>() {
            @Override
            public void onResponse(Call<ResponseJenis> call, Response<ResponseJenis> response) {
                listIJenis = response.body().getListItem();
                List<String> item = new ArrayList<>();
                for (int i = 0; i<listIJenis.size(); i++){
                    item.add(listIJenis.get(i).getNamaJenis());
                }
                spJenis.setItems(item);
            }

            @Override
            public void onFailure(Call<ResponseJenis> call, Throwable t) {

            }
        });
    }

    private void getRuang() {
        Call<ResponseRuang> call = userService.getRuang();
        call.enqueue(new Callback<ResponseRuang>() {
            @Override
            public void onResponse(Call<ResponseRuang> call, Response<ResponseRuang> response) {
                listRuang = response.body().getListItem();
                List<String> item = new ArrayList<>();
                for (int i = 0; i<listRuang.size(); i++){
                    item.add(listRuang.get(i).getNamaRuang());
                }
                spRuang.setItems(item);
            }

            @Override
            public void onFailure(Call<ResponseRuang> call, Throwable t) {

            }
        });
    }

    private void initLayut() {
        edtNama = findViewById(R.id.edtNama);
        edtJumlah = findViewById(R.id.edtJumlah);
        btnTambah = findViewById(R.id.btnTambah);
        spKondisi = findViewById(R.id.spKondisi);
        spKeterangan = findViewById(R.id.spKeterangan);
        spJenis = findViewById(R.id.spJenis);
        spRuang = findViewById(R.id.spRuang);

        userService = APIUtils.getUserService();

        spKondisi.setItems(getResources().getStringArray(R.array.kondisi));
        spKeterangan.setItems(getResources().getStringArray(R.array.keterangan));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tambah Inventaris");
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
