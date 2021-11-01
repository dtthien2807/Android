package com.example.myapplication.chucnangDB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DTS.MonAnDTS;
import com.example.myapplication.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonAnDB {
    SQLiteDatabase db;
    public MonAnDB(Context context){
        CreateDatabase database = new CreateDatabase(context);
        db = database.open();
    }
    public boolean addmon(MonAnDTS monAnDTS){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MON_TENMON, monAnDTS.getTenmon());
        contentValues.put(CreateDatabase.TBL_MON_MALOAI, monAnDTS.getMaloai());
        contentValues.put(CreateDatabase.TBL_MON_GIATIEN, monAnDTS.getGiatien());
        contentValues.put(CreateDatabase.TBL_MON_HINHANH, monAnDTS.getHinhanh());
        contentValues.put(CreateDatabase.TBL_MON_TINHTRANG, "true");
        long ktra = db.insert(CreateDatabase.TBL_MON, null, contentValues);
        if(ktra!=0){
            return true;
        }
        else {
            return false;
        }

    }
    public boolean xoamon(int mamon){
        long ktra = db.delete(CreateDatabase.TBL_MON, CreateDatabase.TBL_MON_MAMON +  " = " + mamon, null);
        if (ktra!=0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean suamon( MonAnDTS monAnDTS, int mamon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MON_TENMON, monAnDTS.getTenmon());
        contentValues.put(CreateDatabase.TBL_MON_MALOAI, monAnDTS.getMaloai());
        contentValues.put(CreateDatabase.TBL_MON_GIATIEN, monAnDTS.getGiatien());
        contentValues.put(CreateDatabase.TBL_MON_HINHANH, monAnDTS.getHinhanh());
        contentValues.put(CreateDatabase.TBL_MON_TINHTRANG, monAnDTS.getTinhtrang());

        long ktra = db.update(CreateDatabase.TBL_MON, contentValues,
                CreateDatabase.TBL_MON + " = " +mamon, null);
        if (ktra!=0){
            return true;
        }
        else {
            return false;
        }
    }
    public List<MonAnDTS> HienThiDSMontheomaLoai(int maloai){
        List<MonAnDTS> monAnDTSList = new ArrayList<>();
        String query = " SELECT * FROM " + CreateDatabase.TBL_MON +" WHERE " +CreateDatabase.TBL_MON_MALOAI+ " = '" +maloai+ "' ";;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            MonAnDTS monAnDTS = new MonAnDTS();
            monAnDTS.setHinhanh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH)));
            monAnDTS.setTenmon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON)));
            monAnDTS.setMamon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MAMON)));
            monAnDTS.setMamon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MALOAI)));
            monAnDTS.setGiatien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN)));
            monAnDTS.setTinhtrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TINHTRANG)));
            monAnDTSList.add(monAnDTS);

            cursor.moveToNext();

        }
        return monAnDTSList;
    }
    public MonAnDTS LayMontheomamon ( int mamon){
        MonAnDTS monAnDTS = new MonAnDTS();
        String query = "SELECT * FROM " + CreateDatabase.TBL_MON + " WHERE " + CreateDatabase.TBL_MON_MAMON + " = " + mamon;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            monAnDTS.setHinhanh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH)));
            monAnDTS.setTenmon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON)));
            monAnDTS.setMamon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MAMON)));
            monAnDTS.setMamon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MALOAI)));
            monAnDTS.setGiatien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN)));
            monAnDTS.setTinhtrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TINHTRANG)));

            cursor.moveToNext();
        }
        return monAnDTS;
    }
}
