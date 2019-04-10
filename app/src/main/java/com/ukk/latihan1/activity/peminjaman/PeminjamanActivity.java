package com.ukk.latihan1.activity.peminjaman;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ukk.latihan1.R;
import com.ukk.latihan1.adapter.AdapterPeminjaman;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListPeminjaman;
import com.ukk.latihan1.modul.response.ResponsePeminjaman;
import com.ukk.latihan1.utils.KeyVal;
import com.ukk.latihan1.utils.SharedPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamanActivity extends AppCompatActivity {

    RecyclerView rvPeminjam;
    SwipeRefreshLayout swipe;

    AdapterPeminjaman adapterPeminjaman;
    UserService userService;
    List<ListPeminjaman> listPeminjaman;

    String id, idLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);
        initLayout();
        getPeminjaman();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPeminjaman();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPeminjaman();
    }

    private void getPeminjaman() {
        swipe.setRefreshing(true);
        Call<ResponsePeminjaman> call;
        if(idLevel.equals("3")){
            call = userService.getPeminjaman(id);
        }else{
            call = userService.getPeminjaman();
        }
        call.enqueue(new Callback<ResponsePeminjaman>() {
            @Override
            public void onResponse(Call<ResponsePeminjaman> call, Response<ResponsePeminjaman> response) {
                listPeminjaman = response.body().getListItem();

                adapterPeminjaman = new AdapterPeminjaman(PeminjamanActivity.this, listPeminjaman);
                rvPeminjam.setAdapter(adapterPeminjaman);
                adapterPeminjaman.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponsePeminjaman> call, Throwable t) {

            }
        });
        swipe.setRefreshing(false);
    }

    private void initLayout() {
        swipe = findViewById(R.id.swipe);
        rvPeminjam = findViewById(R.id.rv);
        rvPeminjam.setLayoutManager(new LinearLayoutManager(this));

        userService = APIUtils.getUserService();
        idLevel = SharedPref.getPref(this, KeyVal.idLevel);
        id = SharedPref.getPref(this, KeyVal.id);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Daftar Peminjaman");
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
