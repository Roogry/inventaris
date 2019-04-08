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
import com.ukk.latihan1.modul.list.ListInventaris;
import com.ukk.latihan1.modul.list.ListRuang;
import com.ukk.latihan1.modul.response.ResponseInventaris;
import com.ukk.latihan1.modul.response.ResponseJenis;
import com.ukk.latihan1.modul.response.ResponseRuang;
import com.ukk.latihan1.utils.KeyVal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PutInventarisActivity extends AppCompatActivity {

    EditText edtNama, edtJumlah;
    Button btnUpdate;
    MaterialSpinner spJenis, spRuang, spKondisi, spKeterangan;

    UserService userService;
    List<ListJenis> listIJenis;
    List<ListRuang> listRuang;
    ListInventaris inventaris;

    String idInvent, keterangan = "Barang Pakai Habis";
    String[] arrKet, arrKondisi;
    int selectedJenis = 0, selectedRuang = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_inventaris);
        initLayut();

        idInvent = getIntent().getStringExtra(KeyVal.id);

        getInvent(idInvent);


        spKeterangan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                keterangan = item;
                Log.e("keterangan : ", keterangan);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putInvent(
                        edtNama.getText().toString(),
                        edtJumlah.getText().toString(),
                        listIJenis.get(spJenis.getSelectedIndex()).getIdJenis(),
                        listRuang.get(spRuang.getSelectedIndex()).getIdRuang(),
                        String.valueOf(spKondisi.getSelectedIndex() + 1),
                        keterangan
                );
            }
        });
    }

    private void getInvent(String idInvent) {
        Call<ResponseInventaris> call = userService.getInventarisId(idInvent);
        call.enqueue(new Callback<ResponseInventaris>() {
            @Override
            public void onResponse(Call<ResponseInventaris> call, Response<ResponseInventaris> response) {
                inventaris = response.body().getListItem().get(0);
                setLayout();
                getJenis();
                getRuang();
            }

            @Override
            public void onFailure(Call<ResponseInventaris> call, Throwable t) {

            }
        });
    }

    private void setLayout() {
        edtNama.setText(inventaris.getNama());
        edtJumlah.setText(inventaris.getJumlah());

        int iKet = Arrays.asList(arrKet).indexOf(inventaris.getKeterangan());
        spKeterangan.setSelectedIndex(iKet);

        int iKon = Integer.parseInt(inventaris.getKondisi()) - 1 ;
        spKondisi.setSelectedIndex(iKon);
    }

    private void putInvent(String nama, String jumlah, String idJenis, String idRuang, String kondisi, String keterangan) {
        if(!nama.isEmpty() && !jumlah.isEmpty()) {
            Call<String> call = userService.putInventaris(idInvent, nama, kondisi, keterangan, jumlah, idJenis, idRuang);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(PutInventarisActivity.this, response.body(), Toast.LENGTH_SHORT).show();
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
                    if(inventaris.getIdJenis().equals(listIJenis.get(i).getIdJenis())){
                        selectedJenis = i;
                    }
                    item.add(listIJenis.get(i).getNamaJenis());
                }
                spJenis.setItems(item);
                spJenis.setSelectedIndex(selectedJenis);
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
                    if(inventaris.getIdRuang().equals(listRuang.get(i).getIdRuang())){
                        selectedRuang = i;
                    }
                    item.add(listRuang.get(i).getNamaRuang());
                }
                spRuang.setItems(item);
                spRuang.setSelectedIndex(selectedRuang);
            }

            @Override
            public void onFailure(Call<ResponseRuang> call, Throwable t) {

            }
        });
    }

    private void initLayut() {
        edtNama = findViewById(R.id.edtNama);
        edtJumlah = findViewById(R.id.edtJumlah);
        btnUpdate = findViewById(R.id.btnUpdate);
        spKondisi = findViewById(R.id.spKondisi);
        spKeterangan = findViewById(R.id.spKeterangan);
        spJenis = findViewById(R.id.spJenis);
        spRuang = findViewById(R.id.spRuang);

        userService = APIUtils.getUserService();

        arrKondisi = getResources().getStringArray(R.array.kondisi);
        arrKet = getResources().getStringArray(R.array.keterangan);
        spKondisi.setItems(arrKondisi);
        spKeterangan.setItems(arrKet);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ubah Inventaris");
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
