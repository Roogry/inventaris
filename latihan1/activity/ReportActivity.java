package com.ukk.latihan1.activity;

import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.ukk.latihan1.R;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListReport;
import com.ukk.latihan1.modul.response.ResponseReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button btnTanggal;
    TextView txtTanggal, txtTransaksiPeminjaman, txtBelumKembali, txtSudahKembali, txtPegawai, txtInventaris;
    MaterialCardView vReport;

    UserService userService;

    String dFrom, dTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initLayout();

        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ReportActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setStartTitle("Dari");
                dpd.setEndTitle("Sampai");
                dpd.setMaxDate(now);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    private void initLayout() {
        txtTanggal = findViewById(R.id.txtTanggal);
        txtTransaksiPeminjaman = findViewById(R.id.txtTransaksiPeminjaman);
        txtBelumKembali = findViewById(R.id.txtBelumKembali);
        txtSudahKembali = findViewById(R.id.txtSudahKembali);
        txtPegawai = findViewById(R.id.txtPegawai);
        txtInventaris = findViewById(R.id.txtInventaris);
        btnTanggal = findViewById(R.id.btnTanggal);
        vReport = findViewById(R.id.vReport);

        userService = APIUtils.getUserService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Report");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        monthOfYear += 1;
        monthOfYearEnd += 1;
        dFrom = year + "-" + monthOfYear + "-" + dayOfMonth;
        dTo = yearEnd + "-" + monthOfYearEnd + "-" + dayOfMonthEnd;

        int dayPlus = dayOfMonthEnd + 1;
        String dToSend = yearEnd + "-" + monthOfYearEnd + "-" + dayPlus;

        getReport(dFrom, dToSend);

        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-d", Locale.US);
            SimpleDateFormat outputFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

            Date dateFromDate = inputFormatter.parse(dFrom);
            Date dateToDate = inputFormatter.parse(dTo);
            txtTanggal.setText(String.format("%s - %s", outputFormatter.format(dateFromDate), outputFormatter.format(dateToDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void getReport(String dFrom, String dTo) {
        Call<ResponseReport> call = userService.getReport(dFrom, dTo);
        call.enqueue(new Callback<ResponseReport>() {
            @Override
            public void onResponse(Call<ResponseReport> call, Response<ResponseReport> response) {
                List<ListReport> report = response.body().getListItem();
                setReport(report);
            }

            @Override
            public void onFailure(Call<ResponseReport> call, Throwable t) {

            }
        });
    }

    private void setReport(List<ListReport> report) {
        txtTransaksiPeminjaman.setText(String.valueOf(report.get(0).getTotalTerpinjam()));
        txtBelumKembali.setText(String.valueOf(report.get(0).getMasihTerpinjam()));
        txtSudahKembali.setText(String.valueOf(report.get(0).getSudahDikembalikan()));
        txtPegawai.setText(String.valueOf(report.get(0).getPetugas()));
        txtInventaris.setText(String.valueOf(report.get(0).getInventaris()));

        vReport.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
