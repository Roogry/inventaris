package com.ukk.latihan1.adapter;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.content.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ukk.latihan1.R;
import com.ukk.latihan1.modul.list.ListPeminjaman;

import java.util.List;


public class AdapterPeminjaman extends RecyclerView.Adapter<AdapterPeminjaman.ViewHolder> {

    private List<ListPeminjaman> listPeminjaman;
    private Context ctx;

    public AdapterPeminjaman(Context ctx, List<ListPeminjaman> listPeminjaman){
        super();
        this.listPeminjaman = listPeminjaman;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterPeminjaman.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_peminjaman, viewGroup, false);
        return new AdapterPeminjaman.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPeminjaman.ViewHolder viewHolder, int i) {
        final ListPeminjaman peminjaman = listPeminjaman.get(i);

        viewHolder.txtNama.setText(peminjaman.getNama());
        viewHolder.txtPeminjam.setText(peminjaman.getNamaPeminjam());
        viewHolder.txtJumlah.setText(peminjaman.getJumlah());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                AlertDialog alert = builder.setTitle("Salin Kode Peminjaman " + peminjaman.getNama())
                        .setMessage(peminjaman.getKodePeminjaman())
                        .setPositiveButton("SALIN!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("Kode Peminjaman", peminjaman.getKodePeminjaman());
                                clipboard.setPrimaryClip(clip);

                                Toast.makeText(ctx, "Kode Tersalin!!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Tidak", null).create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPeminjaman.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtPeminjam, txtJumlah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.txtNama);
            txtPeminjam = itemView.findViewById(R.id.txtPeminjam);
            txtJumlah = itemView.findViewById(R.id.txtJumlah);
        }
    }
}
