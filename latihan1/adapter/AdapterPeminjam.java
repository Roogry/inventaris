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
import com.ukk.latihan1.activity.peminjam.PutPeminjamActivity;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListPeminjam;
import com.ukk.latihan1.utils.KeyVal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPeminjam extends RecyclerView.Adapter<AdapterPeminjam.ViewHolder> {

    private List<ListPeminjam> listPeminjam;
    private Context ctx;

    public AdapterPeminjam(Context ctx, List<ListPeminjam> listPeminjam){
        super();
        this.listPeminjam = listPeminjam;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterPeminjam.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_peminjam, viewGroup, false);
        return new AdapterPeminjam.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPeminjam.ViewHolder viewHolder, int i) {
        final ListPeminjam peminjam = listPeminjam.get(i);

        viewHolder.txtNama.setText(peminjam.getNamaPeminjam());
        viewHolder.txtUsername.setText(peminjam.getUsername());

        final UserService userService = APIUtils.getUserService();

        viewHolder.mvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                AlertDialog alert = builder.setTitle("Hapus " + peminjam.getNamaPeminjam())
                        .setMessage("Yakin ingin menghapus?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<String> call = userService.delPeminjam(peminjam.getIdPeminjam());
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
                Intent intent = new Intent(ctx, PutPeminjamActivity.class);
                intent.putExtra(KeyVal.id, peminjam.getIdPeminjam());
                intent.putExtra(KeyVal.nama, peminjam.getNamaPeminjam());
                intent.putExtra(KeyVal.username, peminjam.getUsername());
                intent.putExtra(KeyVal.password, peminjam.getPassword());
                intent.putExtra(KeyVal.statusPeminjam, peminjam.getStatusPeminjam());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPeminjam.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtUsername;
        ImageView mvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.txtNama);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            mvDelete = itemView.findViewById(R.id.mvDelete);
        }
    }
}
