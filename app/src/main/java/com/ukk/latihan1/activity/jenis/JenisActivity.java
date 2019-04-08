package com.ukk.latihan1.activity.jenis;

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
import com.ukk.latihan1.adapter.AdapterJenis;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListJenis;
import com.ukk.latihan1.modul.response.ResponseJenis;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JenisActivity extends AppCompatActivity {

    RecyclerView rvJenis;
    FloatingActionButton fab;
    SwipeRefreshLayout swipe;

    AdapterJenis adapterJenis;
    UserService userService;
    List<ListJenis> listJenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jenis);
        initLayout();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JenisActivity.this, SetJenisActivity.class);
                startActivity(intent);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJenis();
            }
        });

        getJenis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getJenis();
    }

    private void getJenis() {
        swipe.setRefreshing(true);
        Call<ResponseJenis> call = userService.getJenis();
        call.enqueue(new Callback<ResponseJenis>() {
            @Override
            public void onResponse(Call<ResponseJenis> call, Response<ResponseJenis> response) {
                listJenis = response.body().getListItem();

                adapterJenis = new AdapterJenis(JenisActivity.this, listJenis);
                rvJenis.setAdapter(adapterJenis);
                adapterJenis.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseJenis> call, Throwable t) {

            }
        });
        swipe.setRefreshing(false);
    }

    private void initLayout() {
        swipe = findViewById(R.id.swipe);
        rvJenis = findViewById(R.id.rv);
        rvJenis.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab);

        userService = APIUtils.getUserService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Jenis");
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
