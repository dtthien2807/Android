package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DTS.DonDatDTS;
import com.example.myapplication.DTS.nhanvienDTS;
import com.example.myapplication.R;
import com.example.myapplication.chucnangDB.BanAnDB;
import com.example.myapplication.chucnangDB.nhanvienDB;

import java.util.List;

public class AdapterRecycleViewStatistic extends RecyclerView.Adapter<AdapterRecycleViewStatistic.ViewHolder>{

    Context context;
    int layout;
    List<DonDatDTS> donDatDTSList;
    nhanvienDB nhanvien;
    BanAnDB banAnDB;

    public AdapterRecycleViewStatistic(Context context, int layout, List<DonDatDTS> donDatDTSList){

        this.context =context;
        this.layout = layout;
        this.donDatDTSList = donDatDTSList;
        nhanvien = new nhanvienDB(context);
        banAnDB = new BanAnDB(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewStatistic.ViewHolder holder, int position) {
        DonDatDTS donDatDTO = donDatDTSList.get(position);
        holder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDatDTO.getMaDonDat());
        holder.txt_customstatistic_NgayDat.setText(donDatDTO.getNgayDat());
        if(donDatDTO.getTongTien().equals("0"))
        {
            holder.txt_customstatistic_TongTien.setVisibility(View.INVISIBLE);
        }else {
            holder.txt_customstatistic_TongTien.setVisibility(View.VISIBLE);
        }

        if (donDatDTO.getTinhTrang().equals("true"))
        {
            holder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            holder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        nhanvienDTS nv = nhanvien.LayNVTheoMa(donDatDTO.getMaNV());
        holder.txt_customstatistic_TenNV.setText(nv.getHoten());
        holder.txt_customstatistic_BanDat.setText(banAnDB.LayTenBanTheoMa(donDatDTO.getMaBan()));
    }

    @Override
    public int getItemCount() {
        return donDatDTSList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV,
                txt_customstatistic_BanDat, txt_customstatistic_TongTien,txt_customstatistic_TrangThai;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customstatistic_MaDon = itemView.findViewById(R.id.txt_customstatistic_MaDon);
            txt_customstatistic_NgayDat = itemView.findViewById(R.id.txt_customstatistic_NgayDat);
            txt_customstatistic_TenNV = itemView.findViewById(R.id.txt_customstatistic_TenNV);
            txt_customstatistic_BanDat = itemView.findViewById(R.id.txt_customstatistic_BanDat);
            txt_customstatistic_TongTien = itemView.findViewById(R.id.txt_customstatistic_TongTien);
            txt_customstatistic_TrangThai = itemView.findViewById(R.id.txt_customstatistic_TrangThai);
        }
    }
}
