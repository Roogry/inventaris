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
import com.ukk.latihan1.activity.peminjaman.PinjamActivity;
import com.ukk.latihan1.activity.inventaris.PutInventarisActivity;
import com.ukk.latihan1.api.APIUtils;
import com.ukk.latihan1.api.UserService;
import com.ukk.latihan1.modul.list.ListInventaris;
import com.ukk.latihan1.utils.KeyVal;
import com.ukk.latihan1.utils.SharedPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterInventaris extends RecyclerView.Adapter<AdapterInventaris.ViewHolder> {

    private List<ListInventaris> listInventaris;
    private Context ctx;

    public AdapterInventaris(Context ctx, List<ListInventaris> listInventaris){
        super();
        this.listInventaris = listInventaris;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterInventaris.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_inventaris, viewGroup, false);
        return new AdapterInventaris.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInventaris.ViewHolder viewHolder, int i) {
        final ListInventaris inventaris = listInventaris.get(i);

        viewHolder.txtNama.setText(inventaris.getNama());
        viewHolder.txtjumlah.setText("Tersisa " + inventaris.getJumlah());

        if(!SharedPref.getPref(ctx, KeyVal.idLevel).equals("1")){
            viewHolder.mvEdit.setVisibility(View.GONE);
            viewHolder.mvDelete.setVisibility(View.GONE);
        }

        final UserService userService = APIUtils.getUserService();

        viewHolder.mvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                AlertDialog alert = builder.setTitle("Hapus " + inventaris.getNama())
                .setMessage("Yakin ingin menghapus?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Call<String> call = userService.delnventaris(inventaris.getIdInventaris());
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

        viewHolder.mvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, PutInventarisActivity.class);
                intent.putExtra(KeyVal.id, inventaris.getIdInventaris());
                ctx.startActivity(intent);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, PinjamActivity.class);
                intent.putExtra(KeyVal.id, inventaris.getIdInventaris());
                intent.putExtra(KeyVal.nama, inventaris.getNama());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listInventaris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtjumlah;
        ImageView mvEdit, mvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.txtNama);
            txtjumlah = itemView.findViewById(R.id.txtJumlah);
            mvEdit = itemView.findViewById(R.id.mvEdit);
            mvDelete = itemView.findViewById(R.id.mvDelete);
        }
    }
}
