package com.ukk.latihan1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ukk.latihan1.R;
import com.ukk.latihan1.activity.inventaris.SetInventarisActivity;
import com.ukk.latihan1.activity.jenis.JenisActivity;
import com.ukk.latihan1.activity.peminjam.PeminjamActivity;
import com.ukk.latihan1.activity.peminjaman.KembaliActivity;
import com.ukk.latihan1.activity.peminjaman.PeminjamanActivity;
import com.ukk.latihan1.activity.petugas.PetugasActivity;
import com.ukk.latihan1.activity.ruang.RuangActivity;
import com.ukk.latihan1.adapter.AdapterInventaris;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListInventaris;
import com.ukk.latihan1.modul.response.ResponseInventaris;
import com.ukk.latihan1.utils.KeyVal;
import com.ukk.latihan1.utils.SharedPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtLogout, txtNama;
    RecyclerView rvInventaris;
    FloatingActionButton fab;
    SwipeRefreshLayout swipe;
    SearchView sv;

    private String level, nama;

    AdapterInventaris adapterInventaris;
    UserService userService;
    List<ListInventaris> listInventaris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initDrawer();

        txtNama.setText(nama);

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.logout(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetInventarisActivity.class);
                startActivity(intent);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInventaris();
            }
        });

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.setIconified(false);
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    getInventaris();
                }else {
                    getInventaris(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    getInventaris();
                }else {
                    getInventaris(newText);
                }
                return false;
            }
        });

        getInventaris();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInventaris();
    }

    private void getInventaris() {
        swipe.setRefreshing(true);
        Call<ResponseInventaris> call = userService.getInventaris();
        call.enqueue(new Callback<ResponseInventaris>() {
            @Override
            public void onResponse(Call<ResponseInventaris> call, Response<ResponseInventaris> response) {
                listInventaris = response.body().getListItem();

                adapterInventaris = new AdapterInventaris(MainActivity.this, listInventaris);
                rvInventaris.setAdapter(adapterInventaris);
                adapterInventaris.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseInventaris> call, Throwable t) {

            }
        });
        swipe.setRefreshing(false);
    }

    private void getInventaris(String keyword) {
        swipe.setRefreshing(true);
        Call<ResponseInventaris> call = userService.getInventaris(keyword);
        call.enqueue(new Callback<ResponseInventaris>() {
            @Override
            public void onResponse(Call<ResponseInventaris> call, Response<ResponseInventaris> response) {
                listInventaris = response.body().getListItem();

                adapterInventaris = new AdapterInventaris(MainActivity.this, listInventaris);
                rvInventaris.setAdapter(adapterInventaris);
                adapterInventaris.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseInventaris> call, Throwable t) {

            }
        });
        swipe.setRefreshing(false);
    }

    private void initLayout() {
        txtLogout = findViewById(R.id.txtLogout);
        swipe = findViewById(R.id.swipe);
        rvInventaris = findViewById(R.id.rv);
        rvInventaris.setLayoutManager(new LinearLayoutManager(this));
        sv = findViewById(R.id.sv);

        level = SharedPref.getPref(MainActivity.this, KeyVal.idLevel);
        nama = SharedPref.getPref(MainActivity.this, KeyVal.nama);

        userService = APIUtils.getUserService();
    }

    @SuppressLint("RestrictedApi")
    private void manageItem(NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        View header = navigationView.getHeaderView(0);

        txtNama = header.findViewById(R.id.txtNama);
        fab = findViewById(R.id.fabInventaris);

        if(!level.equals("1")){
            menu.findItem(R.id.navReport).setVisible(false);
            menu.findItem(R.id.navPetugas).setVisible(false);
            menu.findItem(R.id.navJenis).setVisible(false);
            menu.findItem(R.id.navRuang).setVisible(false);
            fab.setVisibility(View.GONE);
        }

        if(level.equals("3")){
            menu.findItem(R.id.navPengembalian).setVisible(false);
            menu.findItem(R.id.navPeminjam).setVisible(false);
        }
    }

    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pilih Inventaris");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        manageItem(navigationView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navPeminjaman) {
            Intent intent = new Intent(MainActivity.this, PeminjamanActivity.class);
            startActivity(intent);
        } else if (id == R.id.navPengembalian) {
            Intent intent = new Intent(MainActivity.this, KembaliActivity.class);
            startActivity(intent);
        } else if (id == R.id.navPetugas) {
            Intent intent = new Intent(MainActivity.this, PetugasActivity.class);
            startActivity(intent);
        } else if (id == R.id.navPeminjam) {
            Intent intent = new Intent(MainActivity.this, PeminjamActivity.class);
            startActivity(intent);
        } else if (id == R.id.navJenis) {
            Intent intent = new Intent(MainActivity.this, JenisActivity.class);
            startActivity(intent);
        } else if (id == R.id.navRuang) {
            Intent intent = new Intent(MainActivity.this, RuangActivity.class);
            startActivity(intent);
        }  else if (id == R.id.navReport) {
            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
