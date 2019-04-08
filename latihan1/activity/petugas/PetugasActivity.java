package com.ukk.latihan1.activity.petugas;

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
import com.ukk.latihan1.adapter.AdapterPetugas;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListPetugas;
import com.ukk.latihan1.modul.response.ResponsePetugas;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetugasActivity extends AppCompatActivity {

    RecyclerView rvPetugas;
    FloatingActionButton fab;
    SwipeRefreshLayout swipe;

    AdapterPetugas adapterPetugas;
    UserService userService;
    List<ListPetugas> listPetugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petugas);
        initLayout();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetugasActivity.this, SetPetugasActivty.class);
                startActivity(intent);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPetugas();
            }
        });

        getPetugas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPetugas();
    }

    private void getPetugas() {
        swipe.setRefreshing(true);
        Call<ResponsePetugas> call = userService.getPetugas();
        call.enqueue(new Callback<ResponsePetugas>() {
            @Override
            public void onResponse(Call<ResponsePetugas> call, Response<ResponsePetugas> response) {
                listPetugas = response.body().getListItem();

                adapterPetugas = new AdapterPetugas(PetugasActivity.this, listPetugas);
                rvPetugas.setAdapter(adapterPetugas);
                adapterPetugas.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponsePetugas> call, Throwable t) {

            }
        });
        swipe.setRefreshing(false);
    }

    private void initLayout() {
        swipe = findViewById(R.id.swipe);
        rvPetugas = findViewById(R.id.rv);
        rvPetugas.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fabPetugas);

        userService = APIUtils.getUserService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Petugas");
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
