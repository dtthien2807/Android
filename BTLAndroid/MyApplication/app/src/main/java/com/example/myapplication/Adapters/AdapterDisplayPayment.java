package com.example.myapplication.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DTS.ThanhToanDTS;
import com.example.myapplication.R;

import java.util.List;

public class AdapterDisplayPayment extends BaseAdapter {

    Context context;
    int layout;
    List<ThanhToanDTS> thanhToanDTSList;
    ViewHolder viewHolder;

    public AdapterDisplayPayment(Context context, int layout, List<ThanhToanDTS> thanhToanDTSList){
        this.context = context;
        this.layout = layout;
        this.thanhToanDTSList = thanhToanDTSList;
    }

    @Override
    public int getCount() {
        return thanhToanDTSList.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhToanDTSList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_custompayment_HinhMon = (ImageView)view.findViewById(R.id.img_custompayment_HinhMon);
            viewHolder.txt_custompayment_TenMon = (TextView)view.findViewById(R.id.txt_custompayment_TenMon);
            viewHolder.txt_custompayment_SoLuong = (TextView)view.findViewById(R.id.txt_custompayment_SoLuong);
            viewHolder.txt_custompayment_GiaTien = (TextView)view.findViewById(R.id.txt_custompayment_GiaTien);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        ThanhToanDTS thanhToanDTS = thanhToanDTSList.get(position);

        viewHolder.txt_custompayment_TenMon.setText(thanhToanDTS.getTenMon());
        viewHolder.txt_custompayment_SoLuong.setText(String.valueOf(thanhToanDTS.getSoLuong()));
        viewHolder.txt_custompayment_GiaTien.setText(String.valueOf(thanhToanDTS.getGiaTien())+" ??");

        byte[] paymentimg = thanhToanDTS.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(paymentimg,0,paymentimg.length);
        viewHolder.img_custompayment_HinhMon.setImageBitmap(bitmap);

        return view;
    }

    public class ViewHolder{
        ImageView img_custompayment_HinhMon;
        TextView txt_custompayment_TenMon, txt_custompayment_SoLuong, txt_custompayment_GiaTien;
    }
}
