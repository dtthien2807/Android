package com.example.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.AddCategoryActivity;
import com.example.myapplication.Activities.HomeActivity;
import com.example.myapplication.Adapters.AdapterRecycleViewCategory;
import com.example.myapplication.Adapters.AdapterRecycleViewStatistic;
import com.example.myapplication.DTS.DonDatDTS;
import com.example.myapplication.DTS.LoaiMonDTS;
import com.example.myapplication.DTS.MonAnDTS;
import com.example.myapplication.R;
import com.example.myapplication.chucnangDB.DonDatDB;
import com.example.myapplication.chucnangDB.LoaiMonDB;
import com.example.myapplication.chucnangDB.MonAnDB;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DisplayHomeFragment extends Fragment implements View.OnClickListener {

    RecyclerView rcv_displayhome_menu, rcv_display_billtoday;
    LinearLayout layout_displayhome_clickmenu,layout_displayhome_clickdatban, layout_display_clickthongke;
    ImageView viewAll_menu, viewAlll_thongke;
    LoaiMonDB loaiMonDB;
    List<LoaiMonDTS> loaiMonDTSList;
    DonDatDB donDatDB;
    List<DonDatDTS> donDatDTSList;
    AdapterRecycleViewCategory adapterRecycleViewCategory;
    AdapterRecycleViewStatistic adapterRecycleViewStatistic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displayhome_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Trang chủ");
        setHasOptionsMenu(true);

        //region Lấy dối tượng view
        rcv_displayhome_menu = (RecyclerView)view.findViewById(R.id.rcv_displayhome_menu);
        rcv_display_billtoday = (RecyclerView)view.findViewById(R.id.rcv_display_billtoday);
        layout_displayhome_clickmenu = (LinearLayout) view.findViewById(R.id.layout_displayhome_clickmenu);
        layout_displayhome_clickdatban = (LinearLayout) view.findViewById(R.id.layout_displayhome_clickdatban);
        layout_display_clickthongke = (LinearLayout) view.findViewById(R.id.layout_display_clickthongke);
        viewAll_menu = (ImageView) view.findViewById(R.id.viewAll_menu);
        viewAlll_thongke = (ImageView) view.findViewById(R.id.viewAlll_thongke);
        //endregion

        //khởi tạo kết nối
        loaiMonDB = new LoaiMonDB(getActivity());
        donDatDB = new DonDatDB(getActivity());

        HienThiDSMon();
        HienThiDonTrongNgay();

        layout_display_clickthongke.setOnClickListener(this);
        layout_displayhome_clickdatban.setOnClickListener(this);
        layout_displayhome_clickmenu.setOnClickListener(this);
        viewAll_menu.setOnClickListener(this);
        viewAlll_thongke.setOnClickListener(this);

        return view;
    }

    private void HienThiDSMon(){
        rcv_displayhome_menu.setHasFixedSize(true);
        rcv_displayhome_menu.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
         loaiMonDTSList= loaiMonDB.LayDSLoaiMon();
        adapterRecycleViewCategory = new AdapterRecycleViewCategory(getActivity(),R.layout.custom_layout_displaycategory,loaiMonDTSList);
        rcv_displayhome_menu.setAdapter(adapterRecycleViewCategory);
        adapterRecycleViewCategory.notifyDataSetChanged();
    }

    private void HienThiDonTrongNgay(){
        rcv_display_billtoday.setHasFixedSize(true);
        rcv_display_billtoday.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaydat= dateFormat.format(calendar.getTime());

        donDatDTSList = donDatDB.LayDSDonDatNgay(ngaydat);
        adapterRecycleViewStatistic = new AdapterRecycleViewStatistic(getActivity(),R.layout.custom_layout_displaystatistic,donDatDTSList);
        rcv_display_billtoday.setAdapter(adapterRecycleViewStatistic);
        adapterRecycleViewCategory.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        NavigationView navigationView = (NavigationView)getActivity().findViewById(R.id.nav_view_trangchu);
        switch (id){
            case R.id.layout_display_clickthongke:

            case R.id.viewAlll_thongke:
                FragmentTransaction tranDisplayStatistic = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayStatistic.replace(R.id.contentView,new DisplayStatisticFragment());
                tranDisplayStatistic.addToBackStack(null);
                tranDisplayStatistic.commit();
                navigationView.setCheckedItem(R.id.nav_statistic);

                break;
            case R.id.layout_displayhome_clickmenu:
                Intent iAddCategory = new Intent(getActivity(), AddCategoryActivity.class);
                startActivity(iAddCategory);
                navigationView.setCheckedItem(R.id.nav_category);
                break;
            case R.id.layout_displayhome_clickdatban:
                FragmentTransaction tranDisplayTable = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayTable.replace(R.id.contentView,new DisplayTableFragment());
                tranDisplayTable.addToBackStack(null);
                tranDisplayTable.commit();
                navigationView.setCheckedItem(R.id.nav_table);

                break;

            case R.id.viewAll_menu:
                FragmentTransaction tranDisplayCategory = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayCategory.replace(R.id.contentView,new DisplayCategoryFragment());
                tranDisplayCategory.addToBackStack(null);
                tranDisplayCategory.commit();
                navigationView.setCheckedItem(R.id.nav_category);

                break;

        }
    }
}

