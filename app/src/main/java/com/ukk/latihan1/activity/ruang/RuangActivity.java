package com.ukk.latihan1.activity.ruang;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.ukk.latihan1.R;
import com.ukk.latihan1.adapter.AdapterRuang;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListRuang;
import com.ukk.latihan1.modul.response.ResponseRuang;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RuangActivity extends AppCompatActivity {

    RecyclerView rvRuang;
    FloatingActionButton fab;
    SwipeRefreshLayout swipe;

    AdapterRuang adapterRuang;
    UserService userService;
    List<ListRuang> listRuang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruang);
        initLayout();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RuangActivity.this, SetRuangActivity.class);
                startActivity(intent);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRuang();
            }
        });

        getRuang();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRuang();
    }

    private void getRuang() {
        swipe.setRefreshing(true);
        Call<ResponseRuang> call = userService.getRuang();
        call.enqueue(new Callback<ResponseRuang>() {
            @Override
            public void onResponse(Call<ResponseRuang> call, Response<ResponseRuang> response) {
                listRuang = response.body().getListItem();

                adapterRuang = new AdapterRuang(RuangActivity.this, listRuang);
                rvRuang.setAdapter(adapterRuang);
                adapterRuang.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseRuang> call, Throwable t) {

            }
        });
        swipe.setRefreshing(false);
    }

    private void initLayout() {
        swipe = findViewById(R.id.swipe);
        rvRuang = findViewById(R.id.rv);
        rvRuang.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab);

        userService = APIUtils.getUserService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ruang");
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
