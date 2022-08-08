package com.example.skckpolrespringsewu.adapter;

//import static android.os.Build.VERSION_CODES.R;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skckpolrespringsewu.model.SkckModel;
import com.example.skckpolrespringsewu.R;

import java.util.List;

public class Skckadapter extends RecyclerView.Adapter<Skckadapter.MyViewHolder>{
    List<SkckModel> mSkckList;
    public Skckadapter.ClickListener clickListener;


    public Skckadapter(List<SkckModel> mSkckList, ClickListener clickListener) {
        this.mSkckList = mSkckList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_skck, parent, false);
        Skckadapter.MyViewHolder holder;
        holder = new MyViewHolder(mView);
        return holder;
    }

    @Override
    public void onBindViewHolder (@NonNull MyViewHolder holder,final int position){
        holder.mNama.setText(mSkckList.get(position).getNama());
        holder.mNik.setText(mSkckList.get(position).getNik());
        holder.mJk.setText("Jenis kelamin : "+mSkckList.get(position).getJk());
        holder.mStatus.setText("Status : "+mSkckList.get(position).getStatus());
        holder.mKecamatan.setText("Kecamatan : "+mSkckList.get(position).getKecamatan());
        holder.mTanggal.setText("Tanggal : "+mSkckList.get(position).getTanggal());
//        holder.mHapus.setOnClickListener(v ->
////                clickListener.dataItem("1","Data berhasil dihapus!")
//        );
        holder.mHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "test",Toast.LENGTH_LONG).show();
                if(clickListener != null){
                    clickListener.dataItem(mSkckList.get(position).getIdSKCK(),"Data berhasil dihapus!");
                }

            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNama,mNik,mJk,mStatus,mKecamatan,mTanggal,mHapus;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.txt_nama);
            mNik = itemView.findViewById(R.id.txt_nik);
            mJk = itemView.findViewById(R.id.txt_jk);
            mStatus = itemView.findViewById(R.id.txt_status);
            mKecamatan = itemView.findViewById(R.id.txt_kecamatan);
            mTanggal = itemView.findViewById(R.id.txt_tanggal);
            mHapus = itemView.findViewById(R.id.btn_delete);
        }
    }
    public int getItemCount () {
        return mSkckList.size();
    }

    public void replaceData(List<SkckModel> daftarSkck) {
        this.mSkckList = daftarSkck;
        notifyDataSetChanged();
    }

    public interface ClickListener{
        void dataItem(String idSkck, String msg);
    }
}

