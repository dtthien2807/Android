package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.DTS.DonDatDTS;
import com.example.myapplication.DTS.nhanvienDTS;
import com.example.myapplication.R;
import com.example.myapplication.chucnangDB.BanAnDB;
import com.example.myapplication.chucnangDB.nhanvienDB;

import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter {

    Context context;
    int layout;
    List<DonDatDTS> donDatDTSList;
    ViewHolder viewHolder;
    nhanvienDB nhanvien;
    BanAnDB banAnDB;

    public AdapterDisplayStatistic(Context context, int layout, List<DonDatDTS> donDatDTSList){
        this.context = context;
        this.layout = layout;
        this.donDatDTSList = donDatDTSList;
        nhanvien = new nhanvienDB(context);
        banAnDB = new BanAnDB(context);
    }

    @Override
    public int getCount() {
        return donDatDTSList.size();
    }

    @Override
    public Object getItem(int position) {
        return donDatDTSList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return donDatDTSList.get(position).getMaDonDat();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txt_customstatistic_MaDon = (TextView)view.findViewById(R.id.txt_customstatistic_MaDon);
            viewHolder.txt_customstatistic_NgayDat = (TextView)view.findViewById(R.id.txt_customstatistic_NgayDat);
            viewHolder.txt_customstatistic_TenNV = (TextView)view.findViewById(R.id.txt_customstatistic_TenNV);
            viewHolder.txt_customstatistic_TongTien = (TextView)view.findViewById(R.id.txt_customstatistic_TongTien);
            viewHolder.txt_customstatistic_TrangThai = (TextView)view.findViewById(R.id.txt_customstatistic_TrangThai);
            viewHolder.txt_customstatistic_BanDat = (TextView)view.findViewById(R.id.txt_customstatistic_BanDat);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DonDatDTS donDatDTS = donDatDTSList.get(position);

        viewHolder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDatDTS.getMaDonDat());
        viewHolder.txt_customstatistic_NgayDat.setText(donDatDTS.getNgayDat());
        viewHolder.txt_customstatistic_TongTien.setText(donDatDTS.getTongTien()+" VNĐ");
        if (donDatDTS.getTinhTrang().equals("true"))
        {
            viewHolder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            viewHolder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        nhanvienDTS nv = nhanvien.LayNVTheoMa(donDatDTS.getMaNV());
        viewHolder.txt_customstatistic_TenNV.setText(nv.getHoten());
        viewHolder.txt_customstatistic_BanDat.setText(banAnDB.LayTenBanTheoMa(donDatDTS.getMaBan()));

        return view;
    }
    public class ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV
                ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_BanDat;

    }
}
