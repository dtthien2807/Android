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

import com.example.myapplication.DTS.MonAnDTS;
import com.example.myapplication.R;

import java.util.List;

public class AdapterDisplayMenu extends BaseAdapter {

    Context context;
    int layout;
    List<MonAnDTS> monAnDTSList;
    Viewholder viewholder;

    //constructor
    public AdapterDisplayMenu(Context context, int layout, List<MonAnDTS> monAnDTSList){
        this.context = context;
        this.layout = layout;
        this.monAnDTSList = monAnDTSList;
    }

    @Override
    public int getCount() {
        return monAnDTSList.size();
    }

    @Override
    public Object getItem(int position) {
        return monAnDTSList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monAnDTSList.get(position).getMamon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewholder.img_custommenu_HinhMon = (ImageView)view.findViewById(R.id.img_custommenu_HinhMon);
            viewholder.txt_custommenu_TenMon = (TextView) view.findViewById(R.id.txt_custommenu_TenMon);
            viewholder.txt_custommenu_TinhTrang = (TextView)view.findViewById(R.id.txt_custommenu_TinhTrang);
            viewholder.txt_custommenu_GiaTien = (TextView)view.findViewById(R.id.txt_custommenu_GiaTien);
            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        MonAnDTS monAnDTS = monAnDTSList.get(position);
        viewholder.txt_custommenu_TenMon.setText(monAnDTS.getTenmon());
        viewholder.txt_custommenu_GiaTien.setText(monAnDTS.getGiatien()+" VN??");

        //hi???n th??? t??nh tr???ng c???a m??n
        if(monAnDTS.getTinhtrang().equals("true")){
            viewholder.txt_custommenu_TinhTrang.setText("C??n m??n");
        }else{
            viewholder.txt_custommenu_TinhTrang.setText("H???t m??n");
        }

        //l???y h??nh ???nh
        if(monAnDTS.getHinhanh() != null){
            byte[] menuimage = monAnDTS.getHinhanh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            viewholder.img_custommenu_HinhMon.setImageBitmap(bitmap);
        }else {
            viewholder.img_custommenu_HinhMon.setImageResource(R.drawable.img_chitiet_coffee);
        }

        return view;
    }

    //t???o viewholer l??u tr??? component
    public class Viewholder{
        ImageView img_custommenu_HinhMon;
        TextView txt_custommenu_TenMon, txt_custommenu_GiaTien,txt_custommenu_TinhTrang;

    }
}