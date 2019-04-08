package com.ukk.latihan1.activity.peminjam;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ukk.latihan1.R;
import com.ukk.latihan1.adapter.AdapterPeminjam;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListPeminjam;
import com.ukk.latihan1.modul.response.ResponsePeminjam;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamActivity extends AppCompatActivity {

    RecyclerView rvPeminjam;
    FloatingActionButton fab;
    SwipeRefreshLayout swipe;

    AdapterPeminjam adapterPeminjam;
    UserService userService;
    List<ListPeminjam> listPeminjam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjam);
        initLayout();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PeminjamActivity.this, SetPeminjamActivity.class);
                startActivity(intent);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPeminjam();
            }
        });

        getPeminjam();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPeminjam();
    }

    private void getPeminjam() {
        swipe.setRefreshing(true);
        Call<ResponsePeminjam> call = userService.getPeminjam();
        call.enqueue(new Callback<ResponsePeminjam>() {
            @Override
            public void onResponse(Call<ResponsePeminjam> call, Response<ResponsePeminjam> response) {
                listPeminjam = response.body().getListItem();

                adapterPeminjam = new AdapterPeminjam(PeminjamActivity.this, listPeminjam);
                rvPeminjam.setAdapter(adapterPeminjam);
                adapterPeminjam.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponsePeminjam> call, Throwable t) {

            }
        });
        swipe.setRefreshing(false);
    }

    private void initLayout() {
        swipe = findViewById(R.id.swipe);
        rvPeminjam = findViewById(R.id.rv);
        rvPeminjam.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fabPeminjam);

        userService = APIUtils.getUserService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Peminjam");
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
