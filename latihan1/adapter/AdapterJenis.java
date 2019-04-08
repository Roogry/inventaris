package com.ukk.latihan1.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ukk.latihan1.R;
import com.ukk.latihan1.activity.jenis.PutJenisActivity;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListJenis;
import com.ukk.latihan1.utils.KeyVal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterJenis extends RecyclerView.Adapter<AdapterJenis.ViewHolder> {
    private List<ListJenis> listJenis;
    private Context ctx;

    public AdapterJenis(Context ctx, List<ListJenis> listJenis){
        super();
        this.listJenis = listJenis;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterJenis.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_jenis, viewGroup, false);
        return new AdapterJenis.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJenis.ViewHolder viewHolder, int i) {
        final ListJenis jenis = listJenis.get(i);

        viewHolder.txtNama.setText(jenis.getNamaJenis());
        viewHolder.txtKode.setText(jenis.getKodeJenis());

        final UserService userService = APIUtils.getUserService();

        viewHolder.mvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                AlertDialog alert = builder.setTitle("Hapus " + jenis.getNamaJenis())
                        .setMessage("Yakin ingin menghapus?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<String> call = userService.delJenis(jenis.getIdJenis());
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Toast.makeText(ctx, response.body(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("Tidak", null).create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, PutJenisActivity.class);
                intent.putExtra(KeyVal.id, jenis.getIdJenis());
                intent.putExtra(KeyVal.nama, jenis.getNamaJenis());
                intent.putExtra(KeyVal.kode, jenis.getKodeJenis());
                intent.putExtra(KeyVal.keterangan, jenis.getKeterangan());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listJenis.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtKode;
        ImageView mvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.txtNama);
            txtKode = itemView.findViewById(R.id.txtKode);
            mvDelete = itemView.findViewById(R.id.mvDelete);
        }
    }
}
