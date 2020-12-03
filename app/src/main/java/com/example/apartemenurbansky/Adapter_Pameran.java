package com.example.apartemenurbansky;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_Pameran extends RecyclerView.Adapter<Adapter_Pameran.HolderData> {
    private Context ctx;
    private List<DataPameran> listData;
    private int id;
    private String jenis_event, nama, no_telp, alamat, waktu, status, keterangan;

    public Adapter_Pameran(Context ctx, List<DataPameran> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }
    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pameran, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataPameran ks= listData.get(position);

        holder.mId.setText(String.valueOf(ks.getId()));
        holder.mNama_sales.setText(ks.getName_pameran());
        holder.mWaktu.setText(ks.getTanggal());
        holder.mStatus.setText(ks.getStatus());



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                dialogPesan.setMessage("Pilih Operasi yang Akan Dilakukan");
                dialogPesan.setCancelable(true);

                id = ks.getId();
                jenis_event = ks.getJenis_event();
                nama =  ks.getName_pameran();
                no_telp = ks.getNo_tlp();
                alamat = ks.getAlamat();
                waktu = ks.getTanggal();
                status = ks.getStatus();
                keterangan = ks.getKeterangan();

                dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteData();
                        dialog.dismiss();
                        ((ReadPameran) ctx).retrieveData();
                    }
                });

                dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        id = ks.getId();
                        jenis_event = ks.getJenis_event();
                        nama =  ks.getName_pameran();
                        no_telp = ks.getNo_tlp();
                        alamat = ks.getAlamat();
                        waktu = ks.getTanggal();
                        status = ks.getStatus();
                        keterangan = ks.getKeterangan();


                        Intent i = new Intent(ctx, UpdatePameran.class);
                        i.putExtra("id", id);
                        i.putExtra("jenis_event", jenis_event);
                        i.putExtra("name_pameran", nama);
                        i.putExtra("no_telp", no_telp);
                        i.putExtra("alamat", alamat);
                        i.putExtra("waktu", waktu);
                        i.putExtra("status", status);
                        i.putExtra("keterangan", keterangan);
                        ctx.startActivity(i);
                    }
                });

                dialogPesan.show();

                return false;
            }
        });
    }

    private void deleteData(){
        ApiInterface ardData = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseData> hapusData = ardData.deletePameran("delete", id);

        hapusData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(ctx, "Gagal Menghubungi Server"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        //private CircleImageView mFoto;
        private TextView  mStatus, mNama_sales, mWaktu, mId;

        public HolderData(@NonNull View itemView) {
            super(itemView);


            mNama_sales = itemView.findViewById(R.id.tv_nama_pelanggan);
            mWaktu = itemView.findViewById(R.id.tv_waktu);
            mStatus = itemView.findViewById(R.id.tv_status);
            mId = itemView.findViewById(R.id.tv_id);

        }

    }
}
